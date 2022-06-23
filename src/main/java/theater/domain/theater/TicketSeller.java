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
        // 전달되는 의존성을 중간에서 사용하면, 디미터 원칙위반 -> 여러 사용처에서 변화에 타격
        // -> 이미 넘어가는 의존성을 직접 사용하지말고, 1곳에서 사용하도록 책임을 위임하고, 결과만 받자.
        //final Money price = movie.calculateFee(screening, count);
        // --> 결국에 전달 받을 거, 미리 전달해서 사용한다.
        // ---> 이렇게 의존성과 직접적인 관계의 객체가 대신 호출해주는 것을 wrapping method라 한다.
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
