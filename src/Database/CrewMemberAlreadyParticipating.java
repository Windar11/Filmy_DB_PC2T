package Database;

public class CrewMemberAlreadyParticipating extends Exception {
    CrewMemberAlreadyParticipating() {
        super("Daná osoba již je spoluautorem");
    }
    CrewMemberAlreadyParticipating(String text) {
        super(text);
    }
}
