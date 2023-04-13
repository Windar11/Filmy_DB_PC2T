public class CrewMemberNotMemberOfGivenFilm extends Exception {
    CrewMemberNotMemberOfGivenFilm() {
        super("Daný spoluautor filmu se ve skutečnosti na filmu nepodílí.");
    }
    CrewMemberNotMemberOfGivenFilm(String text) {
        super(text);
    }
}
