package Database;

public class AnimatedFilmReview extends Review {
    AnimatedFilmReview() {
        super();
    }

    @Override
    public void setPoints(byte points) throws ReviewIncorrectAmmountOfPoints {
        if (points >= 1 && points <= 10) {
            this.points = points;
        }
        else
            throw new ReviewIncorrectAmmountOfPoints();
    }
}
