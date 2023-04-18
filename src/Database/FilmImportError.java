package Database;

public class FilmImportError extends Exception {
    FilmImportError() {
        super("Došlo k chybě při načítání souboru.");
    }
    FilmImportError(String text) {
        super(text);
    }
}
