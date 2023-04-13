package Database;

public class AnimatedFilmReview extends Review {
    AnimatedFilmReview() {
        super();
    }

    @Override
    public void setPoints(short points) throws ReviewIncorrectAmmountOfPoints {
        if (points >= 1 && points <= 10) {
            this.points = points;
        }
        throw new ReviewIncorrectAmmountOfPoints();
    }
}
