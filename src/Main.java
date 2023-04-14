import Database.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DatabaseBackend databaseBackend = new DatabaseBackend();
        if (!databaseBackend.loadDataFromSQL()) {
            System.out.println("Error pri nahravanie");
            // VRACI FALSE POKUD NACITANI Z SQL DATABAZE SELZE - je treba resit v user interface
        }
        // nasledujici try-catch kod je pouze testovaci, zatim zde asi ponechat, kdyby bylo potreba testovat funkci backendu ci databaze,
        // zatim ponechej a ignoruj
        /*try {
            Film one = databaseBackend.addFilm("name", (short)2002, FilmType.ACTED_FILM, (byte)16);
            Film two = databaseBackend.addFilm("Titanic", (short)2016, FilmType.ACTED_FILM, (byte)100);
            databaseBackend.filmAddReview(one, (byte)5, "Dobrej filmeček");
            CrewMember herec = databaseBackend.filmAddCrewMemberNewCrewMember(two, "holaholahejhej");
            databaseBackend.filmUpdateDirectorNewDirector(one, "Borec rejža");
            databaseBackend.filmUpdateDirectorNewDirector(two, "dobrej rejža");
            databaseBackend.filmAddCrewMemberExistingCrewMember(one, herec);
            databaseBackend.filmUpdateDirectorExistingDirector(one, herec);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error se holt nastal");
        }*/

        // pokud vse bude fungovat spravne tak kdyz to prvne otevres a zkusis odtud spustit tak by se to melo bez chyb zapnout a vypnout.
        // teoreticky muzes pridat do idea data source filmDB.db a nad nim zkusit dat nejaky sql dotaz na Film/Crew/Review a melo by ti to neco vyhodit
        // pokud nefunguje databaze tak zkus jit do File->Project Structure->Libraries->Pridat->Vyber knihovnu sqlite-jdbc ze slozky libs v koreni projektu a potvrdit
        // Where is our user interface?

        if (!databaseBackend.saveDataToSQL()) {
            System.out.println("Error pri ukladanie");
            // vraci FALSE pokud ukladani selze
        }
    }
}