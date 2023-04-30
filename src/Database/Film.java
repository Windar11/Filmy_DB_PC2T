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

    public void sortReviews()
    {
        this.filmReviews.sort((o1, o2) -> o1.getPoints() - o2.getPoints());
        /*
        for (int i = 0; i < this.filmReviews.size(); i++)
        {
            for (int j = 0; j < this.filmReviews.size() - 1; j++)
            {
                if (this.filmReviews.get(j).getPoints() < this.filmReviews.get(j + 1).getPoints())
                {
                    Review temp = this.filmReviews.get(j);
                    this.filmReviews.set(j, this.filmReviews.get(j + 1));
                    this.filmReviews.set(j + 1, temp);
                }
            }
        }
        */

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

    protected void setDirector(CrewMember director) {
        this.director = director;
    }
    public CrewMember getDirector() {
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
