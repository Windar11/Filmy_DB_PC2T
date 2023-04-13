package Database;

public class ReviewIncorrectAmmountOfPoints extends Exception {
    ReviewIncorrectAmmountOfPoints() {
        super("Byl zadán špatný počet bodů pro daný typ filmu.");
    }
    ReviewIncorrectAmmountOfPoints(String text) {
        super(text);
    }
}
