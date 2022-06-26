package theater.domain.theater;

import java.util.HashMap;
import java.util.Map;
import theater.domain.Money;
import theater.domain.Movie;
import theater.domain.Reservation;
import theater.domain.Screening;

public class TicketOffice {

    private Money amount;
    private final Map<Theater, Double> commissionRate = new HashMap<>();
    public TicketOffice(Money amount) {
        this.amount = amount;
    }

    public boolean contract(final Theater theater, final Double rate) {
        if (commissionRate.containsKey(theater)) {
            return false;
        }

        commissionRate.put(theater, rate);
        return true;
    }
    public boolean cancel(Theater theater) {
        if (!commissionRate.containsKey(theater)) {
            return false;
        }
        commissionRate.remove(theater);
        return true;
    }

    // seller안에서 전달되던 의존성이 중간 사용되어 불필요한 의존성 추가되었던 것을
    // -> 결국 전달받아 사용되어 의존성이 생기는 곳(office)에서 처리하도록 책임 위임 받음음
    public Money calculateFee(final Movie movie, final Screening screening, final int count) {
        return movie.calculateFee(screening, count);
    }

    public Reservation reserve(Theater theater, Movie movie, Screening screening, int count){
        if (!commissionRate.containsKey(theater)
            || !theater.isValidScreening(movie, screening)
            || !screening.hasSeat(count)) {
            return Reservation.NONE;
        }

        final Reservation reservation = theater.reserve(movie, screening, count);
        if (reservation != Reservation.NONE) {
            final Money sales = calculateFee(movie, screening, count);
            final Money commission = sales.multi(commissionRate.get(theater));
            amount = amount.plus(commission);
            theater.plusAmount(sales.minus(commission));
        }

        return reservation;
    }
}
