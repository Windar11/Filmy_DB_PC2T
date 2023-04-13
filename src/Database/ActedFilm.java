package Database;

public class ActedFilm extends Film{
    ActedFilm(String name, short releaseYear) {
        this.filmType = FilmType.ACTED_FILM;
        this.name = name;
        this.releaseYear = releaseYear;
    }
}
