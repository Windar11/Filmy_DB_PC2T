public class AnimatedFilmReview extends Review {

    public void setPoints(short points) {
        if (points >= 1 && points <= 10)
            this.points = points;
    }
}
