package Database;

public class FilmExportError extends Exception{
    FilmExportError() {
        super("Došlo k chybě při exportu filmu.");
    }
    FilmExportError(String text) {
        super(text);
    }
}
