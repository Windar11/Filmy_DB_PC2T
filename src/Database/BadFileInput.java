package Database;

public class BadFileInput extends Exception{
    BadFileInput() {
        super("Špatný typ filmu");
    }
    BadFileInput(String text) {
        super(text);
    }
}
