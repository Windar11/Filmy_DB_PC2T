import Database.*;

import javax.lang.model.type.NullType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static Database.CrewRole.DIRECTOR;


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
            System.out.println("7. Výpis hercov a dabérov, ktorí sa podieľali na viac ako jednom filme");
            System.out.println("8. Výpis filmov obsahujúcich daného herca alebo dabéra");
            System.out.println("9. Uložiť film do súboru");
            System.out.println("10. Načítať film zo súboru");
            System.out.println("11. Ukončiť program");

            choice = 0;
            Film film;
            CrewMember crewMember;
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();

                switch (choice)
                {
                    case 1:

                        int sc;
                        do
                        {
                            System.out.println("Vyberte druh filmu :");
                            System.out.println("1. Hraný film");
                            System.out.println("2. Animovaný film");
                            try
                            {
                                sc = scanner.nextInt();
                                if (sc == 1)
                                    isAnimated = false;
                                else if (sc==2)
                                    isAnimated = true;

                            } catch (Exception e)
                            {
                                throw new RuntimeException(e);
                            }

                            scanner.nextLine();
                        }while(sc != 1 && sc != 2);



                        System.out.println("Zadajte názov filmu :");
                        String movie_name = scanner.nextLine();


                        System.out.println("Zadajte rok vydania filmu :");
                        short year = getShort(scanner);


                        if(isAnimated)
                        {
                            System.out.println("Zadajte doporučený vek :");
                            byte age = getByte(scanner);
                            film = databaseBackend.addFilm(movie_name, year, FilmType.ANIMATED_FILM, age);
                        }else
                            film = databaseBackend.addFilm(movie_name, year, FilmType.ACTED_FILM, (byte)0);




                        ArrayList<CrewMember> crewMembers = databaseBackend.getAllCrewMembers();
                       int vyber = vyberCrewMembers(databaseBackend,"režiséra", scanner);

                        if(vyber == crewMembers.size())
                        {
                            System.out.println("Zadajte meno režiséra filmu :");
                            String director = scanner.nextLine();
                            try {
                                databaseBackend.filmUpdateDirectorNewDirector(film, director);
                            } catch (FilmNotExists e) {
                                //ignore
                            }
                        }
                        else
                        {
                            try {
                                databaseBackend.filmUpdateDirectorExistingDirector(film, crewMembers.get(vyber));
                            } catch (FilmNotExists e) {
                                //ignore

                            }
                        }

                        do
                        {
                            if(isAnimated)
                                System.out.println("Chcete pridať dabéra?");
                            else
                                System.out.println("Chcete pridať herca?");

                            try
                            {
                                System.out.println("1. Ano");
                                System.out.println("2. Nie");
                                sc = scanner.nextInt();
                                scanner.nextLine();
                                if (sc == 1)
                                {
                                    if(isAnimated)
                                        vyber = vyberCrewMembers(databaseBackend,"dabera", scanner);
                                    else
                                        vyber = vyberCrewMembers(databaseBackend,"herca", scanner);


                                    if(vyber == crewMembers.size())
                                    {
                                        if(isAnimated)
                                            System.out.println("Zadajte meno dabera.");
                                        else
                                            System.out.println("Zadajte meno herca.");

                                        String crewMemberName = scanner.nextLine();
                                        try {
                                            databaseBackend.filmAddCrewMemberNewCrewMember(film, crewMemberName);
                                        } catch (FilmNotExists e) {
                                        }
                                    }
                                    else
                                    {
                                        try {
                                            databaseBackend.filmAddCrewMemberExistingCrewMember(film, crewMembers.get(vyber));
                                        } catch (FilmNotExists e) {
                                        }
                                    }

                                }
                                else if (sc==2)
                                    break;

                            } catch (Exception e)
                            {
                                throw new RuntimeException(e);
                            }
                        }while(true);

                        break;

                    case 2:
                        scanner.nextLine();
                        System.out.println("Zadajte názov filmu, ktorý chcete upraviť.");
                        movie_name = scanner.nextLine();

                        ArrayList<Film> films = databaseBackend.getFilmsByName(movie_name);
                        if(films.size() == 0)
                        {
                            System.out.println("Film neexistuje.");
                            break;

                        }else if(films.size() == 1)
                        {
                            film = films.get(0);

                        } else
                        {
                            do
                            {
                                System.out.println("Vyberte, ktorú variáciu filmu chcete upraviť.");
                                for(int i=0; i< films.size();i++)
                                {
                                    //String directorName = getDirectorName(films.get(i));
                                    String directorName = films.get(i).getDirector().getName();
                                    if(directorName == null)
                                        directorName= "(žiadny režisér)";

                                    System.out.println(i+ ": " +films.get(i).getName()+"    rok: "+films.get(i).getReleaseYear()+"   režisér: "+directorName+"   typ:"+films.get(i).getFilmType());
                                }
                                sc = scanner.nextInt();
                                scanner.nextLine();
                            }while (sc < 0 || sc >= films.size());
                            film = films.get(sc);
                        }
                        scanner.nextLine();
                        int uprava;
                        do
                        {
                            System.out.println("Vyberte, čo chcete upraviť.");
                            System.out.println("0. Ukončiť úpravu filmu");
                            System.out.println("1. Názov filmu");
                            System.out.println("2. Režiséra");
                            System.out.println("3. Rok vydania");
                            if(film.getFilmType() == FilmType.ACTED_FILM)
                                System.out.println("4. Zoznam hercov");
                            else
                            {
                                System.out.println("4. Zoznam dabérov");
                                System.out.println("5. Doporučený vek");
                            }

                            uprava = scanner.nextInt();

                            switch (uprava)
                            {
                                case 0:
                                    System.out.println("Koniec úpravy filmu");
                                    break;

                                case 1:
                                    System.out.println("Zadajte nový názov filmu :");
                                    scanner.nextLine();
                                    movie_name = scanner.nextLine();
                                    try {
                                        databaseBackend.filmUpdateName(film, movie_name);
                                    } catch (FilmNotExists e) {
                                    }
                                    break;

                                case 2:
                                    scanner.nextLine();
                                    crewMembers = databaseBackend.getAllCrewMembers();
                                    vyber = vyberCrewMembers(databaseBackend,"nového režiséra", scanner);

                                    if(vyber == crewMembers.size())
                                    {
                                        System.out.println("Zadajte meno nového režiséra filmu :");
                                        String director = scanner.nextLine();
                                        try {
                                            databaseBackend.filmUpdateDirectorNewDirector(film, director);
                                        } catch (FilmNotExists e) {
                                            //ignore
                                        }
                                    }
                                    else
                                    {
                                        try {
                                            databaseBackend.filmUpdateDirectorExistingDirector(film, crewMembers.get(vyber));
                                        } catch (FilmNotExists e) {
                                            //ignore

                                        }
                                    }
                                    break;

                                case 3:
                                    scanner.nextLine();
                                    System.out.println("Zadajte nový rok vydania filmu :");
                                    year = getShort(scanner);
                                    try {
                                        databaseBackend.filmUpdateReleaseYear(film,year);
                                    } catch (FilmNotExists e) {
                                    }
                                    break;

                                case 4:
                                    int actorChoice=-1;
                                    do
                                    {
                                        if(film.getCrewMembers().size() == 0)
                                        {
                                            System.out.println("Zoznam neobsahuje žiadnych hercov.");
                                        }
                                        else
                                        {
                                            if (film.getFilmType() == FilmType.ACTED_FILM)
                                                System.out.println("Zoznam hercov");
                                            else {
                                                System.out.println("Zoznam daberov");
                                            }
                                            for (int i = 0; i < film.getCrewMembers().size(); i++) {
                                                crewMember = film.getCrewMembers().get(i);


                                                System.out.println(i + ": " + crewMember.getName());
                                            }
                                        }
                                            System.out.println();
                                            System.out.println("Čo si prajte urobiť?");
                                            if (film.getFilmType() == FilmType.ACTED_FILM) {
                                                System.out.println("0. Ukončiť úpravu zoznamu hercov");
                                                System.out.println("1. Pridať herca");
                                                System.out.println("2. Odstrániť herca");
                                            } else {
                                                System.out.println("0. Ukončiť úpravu zoznamu dabérov");
                                                System.out.println("1. Pridať dabéra");
                                                System.out.println("2. Odstrániť dabéra");
                                            }

                                            actorChoice = scanner.nextInt();

                                            switch (actorChoice) {
                                                case 0:
                                                    System.out.println("Koniec úpravy zoznamu");
                                                    break;

                                                case 1:
                                                    crewMembers = databaseBackend.getAllCrewMembers();
                                                    if (film.getFilmType() == FilmType.ANIMATED_FILM)
                                                        vyber = vyberCrewMembers(databaseBackend, "dabéra", scanner);
                                                    else
                                                        vyber = vyberCrewMembers(databaseBackend, "herca", scanner);


                                                    if (vyber == crewMembers.size()) {
                                                        if (film.getFilmType() == FilmType.ANIMATED_FILM)
                                                            System.out.println("Zadajte meno dabéra.");
                                                        else
                                                            System.out.println("Zadajte meno herca.");

                                                        String crewMemberName = scanner.nextLine();
                                                        try {
                                                            databaseBackend.filmAddCrewMemberNewCrewMember(film, crewMemberName);
                                                        } catch (FilmNotExists e) {
                                                        }
                                                    } else {
                                                        try {
                                                            databaseBackend.filmAddCrewMemberExistingCrewMember(film, crewMembers.get(vyber));
                                                        }
                                                        catch (FilmNotExists ignored) {}
                                                        catch (CrewMemberAlreadyParticipating e) {
                                                            if (film.getFilmType() == FilmType.ANIMATED_FILM)
                                                                System.out.println("Dabér již film dabuje");
                                                            else
                                                                System.out.println("Herec již hraje.");
                                                        }
                                                    }
                                                    break;

                                                case 2:

                                                    if(film.getCrewMembers().size() == 0)
                                                    {
                                                        System.out.println("Nie je koho odstrániť!");
                                                        break;
                                                    }

                                                    if (film.getFilmType() == FilmType.ACTED_FILM)
                                                        System.out.println("Vyberte herca na odstránenie");
                                                    else {
                                                        System.out.println("Vyberte dabéra na odstránenie");
                                                    }
                                                    for (int i = 0; i < film.getCrewMembers().size(); i++) {
                                                        crewMember = film.getCrewMembers().get(i);
                                                        if (crewMember == film.getDirector())
                                                            continue;

                                                        System.out.println(i + ": " + crewMember.getName());
                                                    }
                                                    scanner.nextLine();
                                                    int actorToBeDeleted = scanner.nextInt();
                                                    if (actorToBeDeleted >= film.getCrewMembers().size() || actorToBeDeleted < 0) {
                                                        System.out.println("Vybratá položka nexistuje!");
                                                        break;
                                                    } else {
                                                        try {
                                                            databaseBackend.filmRemoveCrewMember(film, film.getCrewMembers().get(actorToBeDeleted));
                                                        } catch (FilmNotExists | CrewMemberNotMemberOfGivenFilm e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }

                                                    break;

                                            }


                                    }while(actorChoice != 0);






                                    break;

                                case 5:
                                    if(film.getFilmType() == FilmType.ANIMATED_FILM)
                                    {
                                        System.out.println("Zadajte nový doporučený vek :");
                                        scanner.nextLine();
                                        byte age = getByte(scanner);
                                        try {
                                            databaseBackend.filmUpdateRecomendedAge(film, age);
                                        } catch (FilmNotExists e) {
                                        }
                                    }else
                                        System.out.println("!Táto možnosť nie je dostupná pri hraných filmoch!");
                                    break;


                            }




                        }while(uprava != 0);


                        break;

                    case 0:

                        break;


                    case 11:
                        System.out.println("Koniec programu");
                        scanner.close();
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
        // We are students. We can pretend we can program all day, but...that up there, that's the jáva...we'll lose.

        if (!databaseBackend.saveDataToSQL()) {
            System.out.println("Error pri ukladanie");
            // vraci FALSE pokud ukladani selze
        }
    }

    public static short getShort(Scanner scanner) {
        short input = 0;
        boolean isCorrect = false;
        while (!isCorrect) {
            try {
                input = Short.parseShort(scanner.nextLine());
                isCorrect = true;
            } catch (Exception e) {
                System.out.println("Zadajte správny formát");
                scanner.nextLine();
            }
        }
        return input;
    }

    public static int getInt(Scanner scanner) {
        int input = 0;
        boolean isCorrect = false;
        while (!isCorrect) {
            try {
                input = Integer.parseInt(scanner.nextLine());
                isCorrect = true;
            } catch (Exception e) {
                System.out.println("Zadajte správny formát");
                scanner.nextLine();
            }
        }
        return input;
    }

    public static byte getByte(Scanner scanner) {
        byte input = 0;
        boolean isCorrect = false;
        while (!isCorrect) {
            try {
                input = Byte.parseByte(scanner.nextLine());
                isCorrect = true;
            } catch (Exception e) {
                System.out.println("Zadajte správny formát");
                scanner.nextLine();
            }
        }
        return input;
    }

    public static int vyberCrewMembers(DatabaseBackend databaseBackend, String type, Scanner scanner)
    {
        ArrayList<CrewMember> crewMembers = databaseBackend.getAllCrewMembers();
        int index = 0;
        for ( index=0; index< crewMembers.size(); index++)
        {
            System.out.println(index+ ": " +crewMembers.get(index).getName());
        }
        System.out.println(index+ ": Nová osoba");

        int vyber = -1;
        do
        {
            System.out.println("Vyberte si "+ type + ".");
            scanner.nextLine();
            vyber = getInt(scanner);

        }while(vyber<0 || vyber >index);
        return vyber;
    }

    /*
    public static String getDirectorName(Film film)
    {
        ArrayList<CrewMember> crewMembers = film.getCrewMembers();
        String directorName = "reziser sa nenasiel";
        for(int j=0; j<crewMembers.size();j++)
        {
            ArrayList<CrewRole> roles = crewMembers.get(j).getRolesInGivenFilm(film);
            for(int k=0; k< roles.size(); k++)
            {
                if (roles.contains(DIRECTOR))
                {
                    directorName = crewMembers.get(j).getName() ;
                }
            }
        }
        return directorName;
    }
    */

}