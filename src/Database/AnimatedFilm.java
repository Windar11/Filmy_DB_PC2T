package Database;

public class AnimatedFilm extends Film{
    AnimatedFilm(String name, short releaseYear) {
        this.filmType = FilmType.ANIMATED_FILM;
        this.name = name;
        this.releaseYear = releaseYear;
    }
}
