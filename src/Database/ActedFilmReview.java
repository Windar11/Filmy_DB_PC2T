package Database;

public class ActedFilmReview extends Review{
    ActedFilmReview() {
        super();
    }

    @Override
    public void setPoints(short points) throws ReviewIncorrectAmmountOfPoints {
        if (points >= 1 && points <= 5) {
            this.points = points;
        }
        throw new ReviewIncorrectAmmountOfPoints();
    }
}
