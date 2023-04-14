package Database;

public abstract class Review {
    protected byte points;
    protected String comment;

    Review() {
        this.comment = "";
        this.points = 0;
    }

    public byte getPoints() {
        return this.points;
    }

    abstract void setPoints(byte points) throws ReviewIncorrectAmmountOfPoints;

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
