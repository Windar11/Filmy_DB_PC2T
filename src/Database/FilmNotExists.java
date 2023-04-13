package Database;

public class FilmNotExists extends Exception{
    FilmNotExists() {
        super("Database.Film není v databázi.");
    }
    FilmNotExists(String text) {
        super(text);
    }
}
