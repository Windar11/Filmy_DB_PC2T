package Database;

public enum CrewRole {
    ACTOR("herec"),
    DUBBER("dabér"),
    DIRECTOR("režisér")
    ;

    private final String text;

    /**
     * @param text
     */
    CrewRole(final String text) {
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
