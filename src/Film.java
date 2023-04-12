import java.util.ArrayList;
import java.util.HashMap;

public abstract class Film {
    protected String name = "";
    protected short releaseYear = 0;
    protected FilmType filmType;
    protected Director director;
    protected ArrayList<CrewMember> crewMembers = new ArrayList<>();
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

    public FilmType getFilmType() {
        return filmType;
    }
    public void setFilmType(FilmType filmType) {
        this.filmType = filmType;
    }

    public void setDirector(Director director) {
        this.director = director;
    }
    public Director getDirector() {
        return this.director;
    }

    public ArrayList<Review> getFilmReviews() {
        return this.filmReviews;
    }
    public void addReview(Review review) {
        this.filmReviews.add(review);
    }

//    public getCrewMemberByName(String name) throws CrewMemberNotExists {
//
//    }

    public ArrayList<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    public void addCrewMember(CrewMember crewMember) {
        this.crewMembers.add(crewMember);
    }
}
