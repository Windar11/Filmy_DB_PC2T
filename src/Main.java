public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ActedFilm film1 = new ActedFilm();
        ActedFilm film2 = new ActedFilm();
        film1.setName("ahoj");
        film2.setName("ahoj");
        System.out.println(film1.equals(film2));
        System.out.println(film1.equals(film1));
    }
}