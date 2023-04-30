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
            Film film = null;
            CrewMember crewMember;
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            scanner.nextLine();

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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CASE 2
                    case 2:

                        System.out.println("Zadajte názov filmu, ktorý chcete upraviť.");
                        String message = ("Vyberte, ktorú variáciu filmu chcete upraviť.");

                        movie_name = scanner.nextLine();

                        ArrayList<Film> films = databaseBackend.getFilmsByName(movie_name);
                        try{film = films.get(selectFilm(scanner,message, films) );}
                        catch (Exception e){
                            break;
                        }

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
                                            scanner.nextLine();
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
                                                                System.out.println("Dabér už film dabuje");
                                                            else
                                                                System.out.println("Herec už hraje.");
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CASE 3

                    case 3:

                        System.out.println("Zadajte názov filmu, ktorý chcete vymazať.");
                        message = ("Vyberte, ktorú variáciu filmu chcete vymazať.");
                        movie_name = scanner.nextLine();

                        films = databaseBackend.getFilmsByName(movie_name);
                        try{film = films.get(selectFilm(scanner,message, films) );}
                        catch (Exception e){
                            break;
                        }

                        try {
                            databaseBackend.removeFilm(film);
                        } catch (FilmNotExists e) {
                        }

                        break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CASE 4
                    case 4:
                        System.out.println("Zadajte názov filmu, ktorý chcete ohodnotiť.");
                        message = ("Vyberte, ktorú variáciu filmu chcete ohodnotiť.");

                        movie_name = scanner.nextLine();

                        films = databaseBackend.getFilmsByName(movie_name);
                        try{film = films.get(selectFilm(scanner,message, films) );}
                        catch (Exception e){
                            break;
                        }

                        byte rating;

                        if(film.getFilmType()== FilmType.ACTED_FILM)
                        {
                            do
                            {
                                System.out.println("Zadajte počet hviezdičiek filmu <1 - najhoršie ; 5 - najlepšie>");
                                scanner.nextLine();
                                rating  = getByte(scanner);
                            }while (rating < 1 || rating > 5);
                        }else
                        {
                            do
                            {
                                System.out.println("Zadajte hodnotenie filmu <1 - najhoršie ; 10 - najlepšie>");
                                scanner.nextLine();
                                rating  = getByte(scanner);
                            }while (rating < 1 || rating > 10);
                        }

                        System.out.println("Slovne ohodnoťte tento film: ");
                        String comment = scanner.nextLine();

                        try {
                            databaseBackend.filmAddReview(film, rating, comment);
                        } catch (FilmNotExists | ReviewIncorrectAmmountOfPoints e) {
                            throw new RuntimeException(e);
                        }


                        break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CASE 5
                    case 5:
                        films = databaseBackend.getAllFilms();
                        System.out.println();
                        System.out.println("Výpis všetkých filmov:");
                        for(int i=0; i< films.size();i++)
                        {
                            String directorName = films.get(i).getDirector().getName();
                            if(directorName == null)
                                directorName= "(žiadny režisér)";
                            System.out.println();
                            System.out.println("________________________________________________________________________");
                            System.out.println(i+": ");
                            System.out.println("Názov filmu:            "+films.get(i).getName());
                            System.out.println("Režisér:                "+directorName);
                            System.out.println("Rok vydania:            "+films.get(i).getReleaseYear());
                            System.out.println("Typ filmu:              "+films.get(i).getFilmType());
                            if(films.get(i).getFilmType() == FilmType.ANIMATED_FILM)
                            {
                                System.out.println("Doporučený vek divákov:  " + films.get(i).getRecommendedAge());
                                System.out.println("Zoznam dabérov:     ");
                            }else System.out.println("Zoznam hercov:     ");
                            for(int j=0; j<films.get(i).getCrewMembers().size();j++)
                            {
                                System.out.println("     "+films.get(i).getCrewMembers().get(j).getName());
                            }

                        }
                        System.out.println("\n\n\n");
                        System.out.println("________________________________________________________________________");
                        System.out.println("________________________________________________________________________");
                        break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CASE 6
                    case 6:
                        System.out.println("Zadajte názov filmu, ktorý chcete vyhľadať.");
                        message = ("Vyberte, ktorú variáciu filmu hľadáte.");

                        movie_name = scanner.nextLine();

                        films = databaseBackend.getFilmsByName(movie_name);
                        try{film = films.get(selectFilm(scanner,message, films) );}
                        catch (Exception e){
                            break;
                        }

                        String directorName = film.getDirector().getName();
                        if(directorName == null)
                            directorName= "(žiadny režisér)";
                        System.out.println();
                        System.out.println("________________________________________________________________________");
                        System.out.println("Názov filmu:            "+film.getName());
                        System.out.println("Režisér:                "+directorName);
                        System.out.println("Rok vydania:            "+film.getReleaseYear());
                        System.out.println("Typ filmu:              "+film.getFilmType());
                        if(film.getFilmType() == FilmType.ANIMATED_FILM)
                        {
                            System.out.println("Doporučený vek divákov:  " + film.getRecommendedAge());
                            System.out.println("Zoznam dabérov:     ");
                        }else System.out.println("Zoznam hercov:     ");
                        for(int j=0; j<film.getCrewMembers().size();j++)
                        {
                            System.out.println("     "+film.getCrewMembers().get(j).getName());
                        }
                        System.out.println("Hodnotenia divákov:     ");

                        for(int j=0; j<film.getFilmReviews().size();j++)
                        {
                            if(film.getFilmType() == FilmType.ACTED_FILM)
                            {
                                System.out.print("     "+ (j+1) +". hodnotenie: ");
                                for(int k =0; k<5;k++)
                                {
                                    if(k<film.getFilmReviews().get(j).getPoints())
                                        System.out.print("*");
                                    else System.out.print(" ");
                                }
                                System.out.println("\t\tKomentár: "+film.getFilmReviews().get(j).getComment());
                            }else
                            {
                                System.out.println("     "+ (j+1) +". hodnotenie: "+film.getFilmReviews().get(j).getPoints() + "/10   Komentár: "+film.getFilmReviews().get(j).getComment());
                            }

                        }
                        System.out.println("\n\n\n");
                        System.out.println("________________________________________________________________________");
                        System.out.println("________________________________________________________________________");
                        break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CASE 7
                    case 7:
                        System.out.println("Zoznam hercov, ktorí hrali vo viac ako 1 filme + zoznam filmov, v ktorých hrali:");
                        crewMembers = databaseBackend.getAllCrewMembersWithMoreFilms();
                        for(int i=0; i< crewMembers.size();i++)
                        {
                            System.out.println();
                            System.out.println("________________________________________________________________________");
                            System.out.println(i+": ");
                            System.out.println("Meno herca:            "+crewMembers.get(i).getName());
                            System.out.println("Zoznam filmov:     ");
                            for(int j=0; j<crewMembers.get(i).getParticipations().size();j++)
                            {
                                System.out.println("\t\t"+crewMembers.get(i).getParticipations().get(j).getName());
                            }

                        }
                        System.out.println("\n\n\n");
                        System.out.println("________________________________________________________________________");
                        System.out.println("________________________________________________________________________");
                        break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CASE 8
                    case 8:
                        System.out.println("Vyberte osobu, ktorej filmy chcete vyhľadať.");
                        crewMembers = databaseBackend.getAllCrewMembers();
                        vyber = vyberExistingCrewMembers(databaseBackend, scanner);
                        if(vyber == -1)
                            break;
                        System.out.println();
                        System.out.println("________________________________________________________________________");
                        System.out.println("Zoznam filmov, na ktorých spolupracovala osoba "+crewMembers.get(vyber).getName()+": ");
                        films = databaseBackend.getAllFilmsWithGivenCrewMemberOrDirector(crewMembers.get(vyber));
                        for(int i=0;i<films.size();i++)
                        {
                            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t"+films.get(i).getName());
                        }
                        System.out.println("________________________________________________________________________");
                        System.out.println("________________________________________________________________________");
                        break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CASE 9
                    case 9:
                        System.out.println("Zadajte názov filmu, ktorý chcete vyhľadať.");
                        message = ("Vyberte, ktorú variáciu filmu chcete uložiť.");

                        movie_name = scanner.nextLine();

                        films = databaseBackend.getFilmsByName(movie_name);
                        try{film = films.get(selectFilm(scanner,message, films) );}
                        catch (Exception e){
                            break;
                        }
                        scanner.nextLine();
                        System.out.println("Zadajte názov súboru, do ktorého chcete uložiť informácie o filme.");
                        String file_name = scanner.nextLine();
                        try {
                            databaseBackend.saveFilmToFile(file_name,film);
                        } catch (FilmExportError e) {
                            throw new RuntimeException(e);
                        }
                        break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CASE 10
                    case 10:
                        scanner.nextLine();
                        System.out.println("Zadajte názov súboru, z ktorého chcete načítať informácie o filme.");
                        file_name = scanner.nextLine();
                        try {
                            film = databaseBackend.loadFilmFromFile(file_name);
                        } catch (FilmImportError e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    default:
                        break;


                    case 11:
                        System.out.println("Koniec programu");
                        scanner.close();
                        break;

                }


        }while(choice != 11);
        if (!databaseBackend.saveDataToSQL()) {
            System.out.println("Error pri ukladanie");
        }
    }

    private static int selectFilm(Scanner scanner, String message, ArrayList<Film> films) {
        Film film;
        int sc;
        if(films.size() == 0)
        {
            System.out.println("Film neexistuje.");
            return -1;

        }else if(films.size() == 1)
        {
            film = films.get(0);

        } else
        {
            do
            {
                System.out.println(message);
                for(int i=0; i< films.size();i++)
                {
                    String directorName = films.get(i).getDirector().getName();
                    if(directorName == null)
                        directorName= "(žiadny režisér)";

                    System.out.println(i+ ": " +films.get(i).getName()+"    rok: "+films.get(i).getReleaseYear()+"   režisér: "+directorName+"   typ:"+films.get(i).getFilmType());
                }
                sc = scanner.nextInt();

            }while (sc < 0 || sc >= films.size());
            return sc;
        }
        return 0;
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

            vyber = getInt(scanner);


        }while(vyber<0 || vyber >index);
        return vyber;
    }

    public static int vyberExistingCrewMembers(DatabaseBackend databaseBackend, Scanner scanner)
    {
        ArrayList<CrewMember> crewMembers = databaseBackend.getAllCrewMembers();
        int index = 0;
        for ( index=0; index< crewMembers.size(); index++)
        {
            System.out.println(index+ ": " +crewMembers.get(index).getName());
        }
        int vyber = -1;
        do
        {
            System.out.println("Vyberte si osobu.");

            vyber = getInt(scanner);


        }while(vyber<0 || vyber >=index);
        return vyber;
    }

}