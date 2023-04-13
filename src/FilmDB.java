import java.util.ArrayList;

public class FilmDB {
    private ArrayList<Film> films = new ArrayList<>();

    public void addFilm(Film film) {
        this.films.add(film);
    }
    public void removeFilm(Film film) {
        films.remove(film);
    }
    public ArrayList<Film> getFilmsByName(String filmName) {
        ArrayList<Film> foundFilms = new ArrayList<>();
        for (Film film: films) {
            if (film.getName().equals(filmName))
                foundFilms.add(film);
        }
        return foundFilms;
    }
    public Film getFilmByIndex(int index) {
        return this.films.get(index);
    }
    public ArrayList<Film> getAllFilms() {
        return this.films;
    }

    public ArrayList<Film> getAllFilmsWithGivenActorOrAnimator(CrewMember crewMember) {
        ArrayList<Film> foundFilms = new ArrayList<>();
        for (Film film: films) {
            if (film.crewMembers.contains(crewMember))
                foundFilms.add(film);
        }
        return foundFilms;
    }

    public boolean updateFilmName(Film film, String name) {
        if (!this.films.contains(film))
            return false;
        this.films.get(this.films.indexOf(film)).setName(name);
        return true;
    }
    public boolean updateFilmReleaseYear(Film film, short releaseYear) {
        if (!this.films.contains(film))
            return false;
        this.films.get(this.films.indexOf(film)).setReleaseYear(releaseYear);
        return true;
    }
    public boolean updateFilmDirector(Film film, CrewMember director) {
        if (!this.films.contains(film))
            return false;
        this.films.get(this.films.indexOf(film)).setDirector(director);
        return true;
    }
    public boolean filmAddCrewMember(Film film, CrewMember crewMember) {

    }
    public boolean filmRemoveCrewMember(Film film, CrewMember crewMember) {

    }
}
