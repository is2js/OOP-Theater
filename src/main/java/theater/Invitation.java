package theater;

public class Invitation {
    public static final Invitation EMPTY = new Invitation(null);
    private final Theater theater;

    public Invitation(final Theater theater) {
        this.theater = theater;
    }
}
