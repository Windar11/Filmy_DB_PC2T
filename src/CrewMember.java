public abstract class CrewMember {
    protected String name;

    public abstract String getOccupation();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
