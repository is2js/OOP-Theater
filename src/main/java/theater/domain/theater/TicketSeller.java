package theater.domain.theater;

import theater.domain.Customer;
import theater.domain.Money;
import theater.domain.Movie;
import theater.domain.Reservation;
import theater.domain.Screening;

public class TicketSeller {

    private TicketOffice ticketOffice;

    public void setTicketOffice(final TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public Reservation reserve(Customer customer,
                               Theater theater,
                               Movie movie,
                               Screening screening,
                               int count) {
        Reservation reservation = Reservation.NONE;
        final Money price = ticketOffice.calculateFee(movie, screening, count);

        if (customer.hasAmount(price)) {
            reservation = ticketOffice.reserve(theater, movie, screening, count);
            if (reservation != Reservation.NONE) {
                customer.minusAmount(price);
            }
        }
        return reservation;
    }
}
