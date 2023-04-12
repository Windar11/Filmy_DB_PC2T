public abstract class CrewMember {
    protected String name;
    protected CrewRole role;

    public CrewRole getRole() {
        return this.role;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
