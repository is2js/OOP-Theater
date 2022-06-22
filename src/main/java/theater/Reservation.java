package theater;

public class Reservation {

    public static final Reservation NONE = new Reservation(null, null, null, 0);

    private final Theater theater;
    private final Movie movie;
    private final Screening screening;
    private final int count;

    public Reservation(final Theater theater, final Movie movie, final Screening screening, final int count) {
        this.theater = theater;
        this.movie = movie;
        this.screening = screening;
        this.count = count;
    }
}
