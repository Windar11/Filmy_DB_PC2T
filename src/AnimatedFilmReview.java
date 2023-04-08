public class AnimatedFilmReview extends Review {
    AnimatedFilmReview() {
        super();
    }

    @Override
    public boolean setPoints(short points) {
        if (points >= 1 && points <= 10) {
            this.points = points;
            return true;
        }
        return false;
    }
}
