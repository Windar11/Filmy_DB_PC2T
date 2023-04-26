import Database.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        DatabaseBackend databaseBackend = new DatabaseBackend();
        if (!databaseBackend.loadDataFromSQL()) {
            System.out.println("Error pri nahravanie");
            // VRACI FALSE POKUD NACITANI Z SQL DATABAZE SELZE - je treba resit v user interface
        }
        int choice = 0;
        Boolean isAnimated = null;

        do
        {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("1. Pridať film");
            System.out.println("2. Upraviť film");
            System.out.println("3. Vymazať film");
            System.out.println("4. Pridať hodnotenie");
            System.out.println("5. Výpis filmov");
            System.out.println("6. Vyhľadávanie filmu");
            System.out.println("7. Výpis hercov a daberov, ktorí sa podieľali na viac ako jednom filme");
            System.out.println("8. Výpis filmov obsahujúcich daného herca alebo dabera");
            System.out.println("9. Uložiť film do súboru");
            System.out.println("10. Načítať film zo súboru");
            System.out.println("11. Ukončiť program");

            choice = 0;
            Film film;
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();

                switch (choice)
                {
                    case 1:
                        System.out.println("Vyberte druh filmu :");
                        System.out.println("1. Hraný film");
                        System.out.println("2. Animovaný film");
                        try
                        {
                            int sc = scanner.nextInt();
                           if (sc == 1)
                               isAnimated = false;
                           else if (sc==2)
                                isAnimated = true;

                        } catch (Exception e)
                        {
                            throw new RuntimeException(e);
                        }

                        System.out.println("Zadajte názov filmu :");
                        String movie_name = scanner.next();


                        System.out.println("Zadajte rok vydania filmu :");
                        short year = scanner.nextShort();

                        System.out.println("Zadajte meno režiséra filmu :");
                        String director = scanner.next();

                        if(isAnimated)
                        {
                            System.out.println("Zadajte doporučený vek :");
                            byte age = scanner.nextByte();
                            film = databaseBackend.addFilm(movie_name, year, FilmType.ANIMATED_FILM, age);
                        }else
                            film = databaseBackend.addFilm(movie_name, year, FilmType.ACTED_FILM, (byte)0);


                        try {
                            databaseBackend.filmUpdateDirectorNewDirector(film, director);
                        } catch (FilmNotExists e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 0:

                        break;
                    case 11:
                        System.out.println("Koniec programu");
                        break;

                }







        }while(choice != 11);


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
        // Gone reduced to attoms.
        // Then we should travel in time before the snap to save it.

        if (!databaseBackend.saveDataToSQL()) {
            System.out.println("Error pri ukladanie");
            // vraci FALSE pokud ukladani selze
        }
    }
}