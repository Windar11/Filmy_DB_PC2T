import java.util.ArrayList;

public class FilmDB {
    ArrayList<Film> films = new ArrayList<>();

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
}
