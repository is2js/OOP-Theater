package theater;

public class Reservation {

    public static final Reservation NONE = new Reservation(null, null, null, 0);

    public final Theater theater;
    public final Movie movie;
    public final Screening screening;
    public final int count;

    public Reservation(final Theater theater, final Movie movie, final Screening screening, final int count) {
        this.theater = theater;
        this.movie = movie;
        this.screening = screening;
        this.count = count;
    }
}
