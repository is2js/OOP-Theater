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

    // 고객이 예매권을 사기 위해, 갑의 거래기능을 이용
    public Reservation reserve(Customer customer, Theater theater, Movie movie, Screening screening, int count) {
        //0) 거래는 중첩if를 만족할때만 성공(특수) & (그외 대부분의 코드분기) 중첩된 if의 else마다 매번 실패값을 return해야하므로
        // -> 애초에 <실패값인 null객체를 반환변수에 미리 할당>해놓고 -> <중첩if의 성공시만 바뀌는, else없는 & earlyReturn없는 코드>를 짠다.
        // --> my) else없는 중첩if문: 미리 맨 위에 else에 해당하는 값이 세팅되어있어야 한다.
        Reservation reservation = Reservation.NONE;
        //1) 물건가격을 받아온다.
        final Money price = movie.calculateFee(screening, count);
        //2) A: 사는놈이 돈있는지 검증해야한다.
        if (customer.hasAmount(price)) {
            //3-1) 돈 있을 경우, 물건을 받아온다. (돈 없는 경우, if에 안걸리고 그대로 NULL물건을 반환)
            // -> 받아온 물건은 NULL물건일 수도 있다.
            reservation = ticketOffice.reserve(theater, movie, screening, count);
            //3-2) B: 물건이 정상적이어야, 사는놈의 돈을 차감한다.
            if (reservation != Reservation.NONE) {
                //4) (돈있고, 물건도 정상) A & B라면, 받아온 고객의 돈을 차감하고, 받아온 물건을 반환한다.
                customer.minusAmount(price);
            }
        }
        //5) 중첩if에 걸려 성공했다면, 사는놈 돈 차감 & NULL아닌 물건을 반환한다.
        //   -> 하나라도 if에 안걸렸다면(else)했다면, 미리 세팅해놓은 default else용 실패값 NULL객체가 반환된다.
        return reservation;
    }
}
