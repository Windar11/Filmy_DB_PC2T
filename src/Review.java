public abstract class Review {
    protected short points;
    protected String comment;

    Review() {
        this.comment = "";
    }

    public short getPoints() {
        return this.points;
    }

    abstract void setPoints(short points);

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
