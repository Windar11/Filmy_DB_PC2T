import java.util.ArrayList;

public class DatabaseBackend {
    private ArrayList<Film> films = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();

    public void addFilm(String name, short releaseYear, FilmType filmType) throws OutOfMemoryError {
        if (filmType==FilmType.ACTED_FILM)
            this.films.add(new ActedFilm(name, releaseYear));
        if (filmType==FilmType.ANIMATED_FILM)
            this.films.add(new AnimatedFilm(name, releaseYear));
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
            for (Review review: film.getFilmReviews()) {
                this.reviews.remove(review);
            }
            this.films.remove(film);
        }
        throw new FilmNotExists();
    }
    public void updateFilmDirectorWithExistingDirector(Film film, CrewMember director) throws FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        CrewMember previousDirector = film.getDirector();
        ArrayList<CrewRole> directorsRolesInFilm = previousDirector.getRolesInGivenFilm(film);
        if (directorsRolesInFilm.contains(CrewRole.DIRECTOR) && directorsRolesInFilm.size()==1)
            previousDirector.removeFilmFromParticipations(film);
        if (previousDirector.getParticipations().isEmpty())
            this.crewMembers.remove(previousDirector);
        film.setDirector(director);
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

    public void updateFilmName(Film film, String name) throws FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        this.films.get(this.films.indexOf(film)).setName(name);
    }
    public void updateFilmReleaseYear(Film film, short releaseYear) throws FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        this.films.get(this.films.indexOf(film)).setReleaseYear(releaseYear);
    }
    public void filmAddCrewMemberExistingCrewMember(Film film, CrewMember crewMember) throws FilmNotExists, CrewMemberNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        if (!film.addCrewMember(crewMember))
            throw new CrewMemberNotExists();
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
    public void filmAddCrewMemberNewCrewMember(Film film, String name) throws OutOfMemoryError, FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        CrewMember crewMember = new CrewMember(name);
        crewMember.addParticipation(film);
        film.addCrewMember(crewMember);
        this.crewMembers.add(crewMember);
    }

    public void filmUpdateDirectorNewDirector(Film film, String name) throws OutOfMemoryError, FilmNotExists {
        if (!this.films.contains(film))
            throw new FilmNotExists();
        CrewMember newDirector = new CrewMember(name);
        newDirector.addParticipation(film);
        this.crewMembers.add(newDirector);
        CrewMember previousDirector = film.getDirector();
        ArrayList<CrewRole> directorsRolesInFilm = previousDirector.getRolesInGivenFilm(film);
        if (directorsRolesInFilm.contains(CrewRole.DIRECTOR) && directorsRolesInFilm.size()==1)
            previousDirector.removeFilmFromParticipations(film);
        if (previousDirector.getParticipations().isEmpty())
            this.crewMembers.remove(previousDirector);
        film.setDirector(newDirector);
    }

    public ArrayList<CrewMember> getAllCrewMembers() {
        return this.crewMembers;
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
}
