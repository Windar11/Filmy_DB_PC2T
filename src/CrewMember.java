import java.util.ArrayList;

public abstract class CrewMember {
    protected String name;
    protected CrewRole role;
    protected ArrayList<Film> participatedIn = new ArrayList<>();

    public void addParticipation(Film film) {
        this.participatedIn.add(film);
    }
    public ArrayList<Film> getParticipations() {
        return this.participatedIn;
    }
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
