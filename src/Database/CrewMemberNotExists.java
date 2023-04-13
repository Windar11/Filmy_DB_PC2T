package Database;

public class CrewMemberNotExists extends Exception{
    public CrewMemberNotExists() {
        super("Herec či Animátor s daným jménem neexistuje.");
    }
    public CrewMemberNotExists(String text) {
        super(text);
    }
}
