public class Actor extends CrewMember{

    Actor(String name) {
        this.name = name;
    }

    @Override
    public String getOccupation() {
        return "herec";
    }
}
