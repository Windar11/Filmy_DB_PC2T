package Database;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseBackend {
    private ArrayList<Film> films = new ArrayList<>();
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();

    public Film addFilm(String name, short releaseYear, FilmType filmType, byte recommendedAge) {
        Film film;
        if (filmType==FilmType.ACTED_FILM)
            film = new ActedFilm(name, releaseYear, recommendedAge);
        else
            film = new AnimatedFilm(name, releaseYear);
        this.films.add(film);
        return film;
    }
    public ArrayList<Film> getAllFilms() {
        return this.films;
    }
    public boolean removeFilm(Film film) throws FilmNotExists {
        if (this.films.contains(film)) {
            for (CrewMember crewMember: film.getCrewMembers()) {
                crewMember.removeFilmFromParticipations(film);
                if (crewMember.getParticipations().size()==0)
                    this.crewMembers.remove(crewMember);
            }
            this.films.remove(film);
        }
        throw new FilmNotExists();
    }
    public void filmUpdateDirectorExistingDirector(Film film, CrewMember director) throws FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        CrewMember previousDirector = film.getDirector();
        if (previousDirector!=null) {
            ArrayList<CrewRole> directorsRolesInFilm = previousDirector.getRolesInGivenFilm(film);
            if (directorsRolesInFilm.contains(CrewRole.DIRECTOR) && directorsRolesInFilm.size()==1)
                previousDirector.removeFilmFromParticipations(film);
            if (previousDirector.getParticipations().isEmpty())
                this.crewMembers.remove(previousDirector);
        }
        film.setDirector(director);
        try {
            director.addParticipation(film);
        }
        catch (CrewMemberAlreadyParticipating ignored) {}
    }
    public ArrayList<Film> getFilmsByName(String filmName) {
        ArrayList<Film> foundFilms = new ArrayList<>();
        for (Film film: films) {
            if (film.getName().equals(filmName))
                foundFilms.add(film);
        }
        return foundFilms;
    }

    public ArrayList<Film> getAllFilmsWithGivenCrewMemberOrDirector(CrewMember crewMember) {
        ArrayList<Film> foundFilms = new ArrayList<>();
        for (Film film: films) {
            if (film.crewMembers.contains(crewMember) || film.director.equals(crewMember))
                foundFilms.add(film);
        }
        return foundFilms;
    }

    public void filmUpdateName(Film film, String name) throws FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        this.films.get(this.films.indexOf(film)).setName(name);
    }
    public void filmUpdateReleaseYear(Film film, short releaseYear) throws FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        this.films.get(this.films.indexOf(film)).setReleaseYear(releaseYear);
    }
    public void filmUpdateRecomendedAge(Film film, byte recomendedAge) throws FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        this.films.get(this.films.indexOf(film)).setRecommendedAge(recomendedAge);
    }
    public void filmAddCrewMemberExistingCrewMember(Film film, CrewMember crewMember) throws FilmNotExists, CrewMemberAlreadyParticipating {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        if (!film.addCrewMember(crewMember))
            throw new CrewMemberAlreadyParticipating();
        else
            crewMember.addParticipation(film);
    }
    public void filmRemoveCrewMember(Film film, CrewMember crewMember) throws FilmNotExists, CrewMemberNotMemberOfGivenFilm {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        if (!film.getCrewMembers().contains(crewMember))
            throw new CrewMemberNotMemberOfGivenFilm();
        film.removeCrewMember(crewMember);
        ArrayList<CrewRole> crewMemberRolesInFilm = crewMember.getRolesInGivenFilm(film);
        if (!crewMemberRolesInFilm.contains(CrewRole.DIRECTOR))
            crewMember.removeFilmFromParticipations(film);
        if (crewMember.getParticipations().isEmpty())
            this.crewMembers.remove(crewMember);
    }
    public CrewMember filmAddCrewMemberNewCrewMember(Film film, String name) throws FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        CrewMember crewMember = new CrewMember(name);
        try {
            crewMember.addParticipation(film);
        } catch (CrewMemberAlreadyParticipating ignored) {}
        film.addCrewMember(crewMember);
        this.crewMembers.add(crewMember);
        return crewMember;
    }

    public CrewMember filmUpdateDirectorNewDirector(Film film, String name) throws FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        CrewMember newDirector = new CrewMember(name);
        try {
            newDirector.addParticipation(film);
        }
        catch (CrewMemberAlreadyParticipating ignored) {}
        this.crewMembers.add(newDirector);
        CrewMember previousDirector = film.getDirector();
        if (previousDirector!=null) {
            ArrayList<CrewRole> directorsRolesInFilm = previousDirector.getRolesInGivenFilm(film);
            if (directorsRolesInFilm.contains(CrewRole.DIRECTOR) && directorsRolesInFilm.size()==1)
                previousDirector.removeFilmFromParticipations(film);
            if (previousDirector.getParticipations().isEmpty())
                this.crewMembers.remove(previousDirector);
        }
        film.setDirector(newDirector);
        return newDirector;
    }

    public ArrayList<CrewMember> getAllCrewMembers() {
        return this.crewMembers;
    }

    public ArrayList<CrewMember> getCrewMembersByName(String memberName) {
        ArrayList<CrewMember> foundMembers = new ArrayList<>();
        for (CrewMember crewMember: this.crewMembers) {
            if (crewMember.getName().equals(memberName))
                foundMembers.add(crewMember);
        }
        return foundMembers;
    }
    public ArrayList<CrewMember> getAllCrewMembersWithMoreFilms() {
        ArrayList<CrewMember> crewMembersWithMoreFilms = new ArrayList<>();
        for (CrewMember crewMember : this.crewMembers) {
            if (crewMember.participatedIn.size()>=2) {
                crewMembersWithMoreFilms.add(crewMember);
            }
        }
        return crewMembersWithMoreFilms;
    }
    public void filmAddReview(Film film, byte score, String commentary) throws FilmNotExists, ReviewIncorrectAmmountOfPoints {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        if (film.getFilmType()==FilmType.ANIMATED_FILM) {
            Review review = new AnimatedFilmReview();
            review.setPoints(score);
            review.setComment(commentary);
            film.addReview(review);
        }
        if (film.getFilmType()==FilmType.ACTED_FILM) {
            Review review = new ActedFilmReview();
            review.setPoints(score);
            review.setComment(commentary);
            film.addReview(review);
        }
    }

    public boolean crewMemberExists(CrewMember crewMember) {
        return this.crewMembers.contains(crewMember);
    }

    public boolean loadDataFromSQL() {
        SQLHandler sqlHandler = new SQLHandler();
        try {
            sqlHandler.establishConnection();
            sqlHandler.createStatements();
            ResultSet loadFilms = sqlHandler.selectAllFilms();
            ResultSet loadCMs = sqlHandler.selectAllCrewMembers();
            ResultSet loadRevs = sqlHandler.selectAllReviews();
            // TODO ADD INNER CODE FOR DB LOADING
            while(loadFilms.next()) {
                if (loadFilms.getString("film_type").equals(FilmType.ACTED_FILM.toString()))
                    addFilm(loadFilms.getString("name"), loadFilms.getShort("release_year"), FilmType.ACTED_FILM, loadFilms.getByte("recommended_age"));
                else
                    addFilm(loadFilms.getString("name"), loadFilms.getShort("release_year"), FilmType.ANIMATED_FILM, loadFilms.getByte("recommended_age"));


            }
            while(loadRevs.next()) {
                Film film = this.films.get(loadRevs.getInt("film_id"));
                filmAddReview(film, loadRevs.getByte("score"), loadRevs.getString("comment"));
            }
            int lastCMIndex = -1;
            while(loadCMs.next()) {
                Film film = this.films.get(loadCMs.getInt("film_id"));
                int currentCMIndex = loadCMs.getInt("id");
                if (lastCMIndex != currentCMIndex) {
                    if (loadCMs.getString("occupation").equals(CrewRole.DIRECTOR.toString())) {
                        filmUpdateDirectorNewDirector(film, loadCMs.getString("name"));
                    }
                    else {
                        filmAddCrewMemberNewCrewMember(film, loadCMs.getString("name"));
                    }
                }
                else {
                    CrewMember crewMember = this.getAllCrewMembers().get(currentCMIndex);
                    if (loadCMs.getString("occupation").equals(CrewRole.DIRECTOR.toString())) {
                        filmUpdateDirectorExistingDirector(film, crewMember);
                    }
                    else {
                        try {
                            filmAddCrewMemberExistingCrewMember(film, crewMember);
                        }
                        catch (CrewMemberAlreadyParticipating ignored) {}
                    }
                }
                lastCMIndex = currentCMIndex;
            }

            sqlHandler.closeConnection();
        }
        catch (SQLNullConnection | SQLException | FilmNotExists | ReviewIncorrectAmmountOfPoints e) {
            try {
                sqlHandler.closeConnection();
            }
            catch (SQLException | SQLNullConnection ignore) {}
            return false;
        }
        return true;

    }

    public boolean saveDataToSQL(){
        SQLHandler sqlHandler = new SQLHandler();
        try {
            sqlHandler.establishConnection();
            sqlHandler.createTablesIfNotExist();
            sqlHandler.createStatements();
            sqlHandler.truncateAllTables();
            for (int filmIndex=0;filmIndex<this.films.size();filmIndex++) {
                Film film = this.films.get(filmIndex);
                sqlHandler.insertFilm(filmIndex, film.getName(), film.getReleaseYear(), film.getRecommendedAge(), film.getFilmType().toString());
                for (int revIndex=0;revIndex<film.getFilmReviews().size();revIndex++) {
                    Review review = film.getFilmReviews().get(revIndex);
                    sqlHandler.insertReview(filmIndex, review.getPoints(), review.getComment());
                }
            }
            for (int crewIndex=0;crewIndex<this.getAllCrewMembers().size();crewIndex++) {
                CrewMember crewMember = this.crewMembers.get(crewIndex);
                for (Film participatedIn: crewMember.getParticipations()) {
                    int filmIndex = this.films.indexOf(participatedIn);
                    for (CrewRole role: crewMember.getRolesInGivenFilm(participatedIn)) {
                        sqlHandler.insertCrewMember(crewIndex, filmIndex, crewMember.getName(), role.toString());
                    }
                }
            }
            sqlHandler.closeConnection();
        }
        catch (SQLNullConnection | SQLException e) {
            try {
                sqlHandler.closeConnection();
            }
            catch (SQLException | SQLNullConnection ignore) {}
            return false;
        }
        return true;
    }

    public Film loadFilmFromFile(String fileName) throws FilmImportError {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("fileName", StandardCharsets.UTF_8));
            JSONTokener tokener = new JSONTokener(bufferedReader);
            JSONObject filmJSON = new JSONObject(tokener);
            Film film;
            String filmTypeName = filmJSON.getString("filmType");
            String filmName = filmJSON.getString("filmName");
            short releaseYear = (short) filmJSON.getInt("releaseYear");
            byte recommendedAge = (byte) filmJSON.getInt("recommendedAge");
            String directorName = filmJSON.getString("director");
            JSONArray reviews = filmJSON.getJSONArray("reviews");
            JSONArray crewMembers = filmJSON.getJSONArray("crewMembers");
            FilmType filmType;
            if (filmTypeName.equals(FilmType.ACTED_FILM.toString()))
                filmType = FilmType.ACTED_FILM;
            else if (filmTypeName.equals(FilmType.ANIMATED_FILM.toString()))
                filmType = FilmType.ANIMATED_FILM;
            else
                throw new BadFileInput();

            ArrayList<Film> filmsWithSameName = this.getFilmsByName(filmName);
            if (filmsWithSameName.size() != 0)
                filmName += " !i";
            switch (filmType) {
                case ACTED_FILM -> film = new ActedFilm(filmName, releaseYear, recommendedAge);
                case ANIMATED_FILM -> film = new AnimatedFilm(filmName, releaseYear);
                default -> throw new BadFileInput();
            }

            ArrayList<CrewMember> crewMembersWithSameName = this.getCrewMembersByName(directorName);
            if (crewMembersWithSameName.size() != 0)
                directorName += " !i";
            this.filmUpdateDirectorNewDirector(film, directorName);
            for (int i = 0; i < reviews.length(); i++) {
                JSONObject reviewJSON = reviews.getJSONObject(i);
                String comment = reviewJSON.getString("comment");
                Byte points = (byte) reviewJSON.getInt("score");
                Review review;
                switch (filmType) {
                    case ACTED_FILM -> review = new ActedFilmReview();
                    case ANIMATED_FILM -> review = new AnimatedFilmReview();
                    default -> throw new BadFileInput();
                }
                review.setComment(comment);
                review.setPoints(points);
                film.addReview(review);
            }
            for (int i = 0; i < crewMembers.length(); i++) {
                String crewMemberName = crewMembers.getString(i);
                crewMembersWithSameName = this.getCrewMembersByName(crewMemberName);
                if (crewMembersWithSameName.size() != 0)
                    crewMemberName += " !i";
                this.filmAddCrewMemberNewCrewMember(film, crewMemberName);
            }
            return film;
        }
        catch (Exception e) {
            throw new FilmImportError();
        }
    }

    public void saveFilmToFile(String fileName, Film film) throws FilmExportError {
        try {
            JSONObject filmJSON = new JSONObject();
            filmJSON.put("filmName", film.getName());
            filmJSON.put("releaseYear", film.getReleaseYear());
            filmJSON.put("filmType", film.getFilmType().toString());
            filmJSON.put("director", film.getDirector().getName());
            filmJSON.put("recommendedAge", film.getRecommendedAge());
            JSONArray reviewsJSON = new JSONArray();
            for (Review review : film.getFilmReviews()) {
                JSONObject reviewJSON = new JSONObject();
                reviewJSON.put("comment", review.getComment());
                reviewJSON.put("score", review.getPoints());
                reviewsJSON.put(reviewJSON);
            }
            filmJSON.put("reviews", reviewsJSON);
            JSONArray crewMembersJSON = new JSONArray();
            for (CrewMember crewMember : film.getCrewMembers()) {
                crewMembersJSON.put(crewMember.getName());
            }
            filmJSON.put("crewMembers", crewMembersJSON);
            PrintWriter writer = new PrintWriter(fileName, StandardCharsets.UTF_8);
            writer.println(filmJSON);
            writer.close();
        }
        catch (Exception e) {
            throw new FilmExportError();
        }
    }
}
