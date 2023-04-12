public class ActedFilm extends Film{

    @Override
    public void addCrewMember(String name) {
        this.filmCrew.put(name, new Actor(name));
    }
}
