package Database;

import java.util.ArrayList;

public abstract class Film {
    protected String name = "";
    protected short releaseYear = 0;
    protected FilmType filmType;
    protected CrewMember director;
    protected byte recommendedAge;
    protected ArrayList<CrewMember> crewMembers = new ArrayList<>();
    protected ArrayList<Review> filmReviews = new ArrayList<>();


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

    protected void setDirector(CrewMember director) {
        this.director = director;
    }
    protected CrewMember getDirector() {
        return this.director;
    }

    public ArrayList<Review> getFilmReviews() {
        return this.filmReviews;
    }
    public void addReview(Review review) {
        this.filmReviews.add(review);
    }

    public ArrayList<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    protected boolean addCrewMember(CrewMember crewMember) {
        if (this.crewMembers.contains(crewMember))
            return false;
        this.crewMembers.add(crewMember);
        return true;
    }

    protected void removeCrewMember(CrewMember crewMember) {
        this.crewMembers.remove(crewMember);
    }
    public void setRecommendedAge(byte recommendedAge) {
        this.recommendedAge = recommendedAge;
    }
    public byte getRecommendedAge() {
        return this.recommendedAge;
    }
}
