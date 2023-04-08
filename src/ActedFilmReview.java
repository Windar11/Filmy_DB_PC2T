public class ActedFilmReview extends Review{
    ActedFilmReview() {
        super();
    }

    @Override
    public boolean setPoints(short points) {
        if (points >= 1 && points <= 5) {
            this.points = points;
            return true;
        }
        return false;
    }
}
