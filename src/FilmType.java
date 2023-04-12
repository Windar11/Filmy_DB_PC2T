public enum FilmType {
    ACTED_FILM("hraný"),
    ANIMATED_FILM("animovaný")
    ;

    private final String text;

    /**
     * @param text
     */
    FilmType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
