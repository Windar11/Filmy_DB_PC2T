public class ActedFilm extends Film{


    @Override
    public boolean addReview(String comment, short points) {
        ActedFilmReview review = new ActedFilmReview();
        if (!review.setPoints(points))
            return false;
        review.setComment(comment);
        this.filmReviews.add(review);
        return true;
    }

    @Override
    public void addCrewMember(String name) {
        this.filmCrew.put(name, new Actor(name));
    }
}
