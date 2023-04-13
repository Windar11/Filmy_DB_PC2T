public class Main {
    public static void main(String[] args) {
        DatabaseBackend databaseBackend = new DatabaseBackend();
        databaseBackend.addFilm("ahoj", (short)2002, FilmType.ANIMATED_FILM);
    }
}