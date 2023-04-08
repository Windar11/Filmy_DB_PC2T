import java.util.ArrayList;
import java.util.HashMap;

public abstract class Film {
    protected String name = "";
    protected short releaseYear = 0;
    protected String directorName = "";
    protected HashMap<String, CrewMember> filmCrew = new HashMap<>();
    protected ArrayList<Review> filmReviews = new ArrayList<>();

    Film() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public short getReleaseYear() {
        return this.releaseYear;
    }
    public void setReleaseYear(short releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
    public String getDirectorName() {
        return this.directorName;
    }

    public ArrayList<Review> getFilmReviews() {
        return this.filmReviews;
    }
    public abstract boolean addReview(String comment, short points);

    public CrewMember getCrewMemberByName(String name) throws CrewMemberNotExists {
        if (!this.filmCrew.containsKey(name))
            throw new CrewMemberNotExists();
        return this.filmCrew.get(name);
    }

    public ArrayList<CrewMember> getAllCrewMembers() {
        ArrayList<CrewMember> crewMembers = new ArrayList<>();
        crewMembers.
        for (CrewMember crewMember: this.filmCrew.values()) {
            crewMembers.add(crewMember);
        }
        return crewMembers;
    }

    public abstract void addCrewMember(String name);
}
