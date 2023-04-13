package Database;

public abstract class Review {
    protected short points;
    protected String comment;

    Review() {
        this.comment = "";
        this.points = 0;
    }

    public short getPoints() {
        return this.points;
    }

    abstract void setPoints(short points) throws ReviewIncorrectAmmountOfPoints;

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
