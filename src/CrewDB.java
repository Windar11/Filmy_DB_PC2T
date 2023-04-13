import java.util.ArrayList;

public class CrewDB {
    private ArrayList<CrewMember> crewMembers = new ArrayList<>();

    public void addCrewMember(CrewMember crewMember) {
        this.crewMembers.add(crewMember);
    }

    public ArrayList<CrewMember> getAllCrewMembers() {
        return this.crewMembers;
    }

    public void removeCrewMember(CrewMember crewMember) {
        this.crewMembers.remove(crewMember);
    }

    public ArrayList<CrewMember> getAllCrewMembersWithMoreFilms() {
        ArrayList<CrewMember> crewMembersWithMoreFilms = new ArrayList<>();
        for (CrewMember crewMember : this.crewMembers) {
            if (crewMember.participatedIn.size()>=2) {
                crewMembersWithMoreFilms.add(crewMember);
            }
        }
        return crewMembersWithMoreFilms;
    }
}
