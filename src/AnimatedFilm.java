public class AnimatedFilm extends Film{


    @Override
    public boolean addReview(String comment, short points) {
        AnimatedFilmReview review = new AnimatedFilmReview();
        if (!review.setPoints(points))
            return false;
        review.setComment(comment);
        this.filmReviews.add(review);
        return true;
    }

    @Override
    public void addCrewMember(String name) {
        this.filmCrew.put(name, new Animator(name));
    }
}
