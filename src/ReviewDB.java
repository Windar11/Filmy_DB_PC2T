import java.util.ArrayList;

public class ReviewDB {
    private ArrayList<Review> reviews = new ArrayList<>();

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public ArrayList<Review> getAllReviews() {
        return this.reviews;
    }
}
