import java.util.ArrayList;

public abstract class CrewMember {
    protected String name;
    protected ArrayList<Film> participatedIn = new ArrayList<>();

    public void addParticipation(Film film) {
        this.participatedIn.add(film);
    }
    public ArrayList<Film> getParticipations() {
        return this.participatedIn;
    }
    public ArrayList<CrewRole> getRolesInGivenFilm(Film film) {
        ArrayList<CrewRole> rolesInFilm = new ArrayList<>();
        if (!this.participatedIn.contains(film))
            return rolesInFilm;
        Film participatedFilm = this.participatedIn.get(this.participatedIn.indexOf(film));
        if (participatedFilm.director == this)
            rolesInFilm.add(CrewRole.DIRECTOR);
        if (participatedFilm.getCrewMembers().contains(this)) {
            switch (participatedFilm.getFilmType()) {
                case ACTED_FILM:
                    rolesInFilm.add(CrewRole.ACTOR);
                    break;
                case ANIMATED_FILM:
                    rolesInFilm.add(CrewRole.ANIMATOR);
                    break;
                default:
                    break;
            }
        }
        return rolesInFilm;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
