package Database;

public class SQLNullConnection extends Exception{
    SQLNullConnection() {
        super("Není inicializované spojení s databází.");
    }
    SQLNullConnection(String text) {
        super(text);
    }
}
