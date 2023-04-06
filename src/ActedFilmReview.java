public class ActedFilmReview extends Review{

    public void setPoints(short points) {
        if (points >= 1 && points <= 5)
            this.points = points;
    }
}
