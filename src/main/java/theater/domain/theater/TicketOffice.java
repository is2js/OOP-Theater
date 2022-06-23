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

    // 계약: 거래theater + 거래조건 저장하기
    // -> 내부에서 처리되며, 성공/실패를 boolean으로 알려주어, 갑 저장성공시 -> 을 저장하는 순서로 하게 한다.
    public boolean contract(final Theater theater, final Double rate) {
        // 저장할 땐, 상위개념 중복여부부터 검사한다.
        if (commissionRate.containsKey(theater)) {
            return false;
        }

        commissionRate.put(theater, rate);
        return true;
    }
    // 계약취소 -> 삭제개념으로 존재여부 검사 + 내부처리로서 t/f반환
    // -> 계약취소도 상대방간 이루어져야하는데, 갑(office)부터 remove하고 t/f를 받아 -> 을(theater)가 remove한다.
    public boolean cancel(Theater theater) {
        if (!commissionRate.containsKey(theater)) {
            return false;
        }
        commissionRate.remove(theater);
        return true;
    }

    // seller가 이용할 ticketoffice에게 예매권 발행 요청 메서드
    // seller(판매자)가 생성요청하는 데이터객체(Reservation)의 정보가
    // 1) 나와 계약된 생산자로 요청하는지 by theater 인자
    // 2) 계약된 생산자가 만들 수 있는 것인지(유효한 정보인지) by movie, screening 인자
    // 3) 생산 가능한지(이용자석수 남아있는지) by screening 인자
    // 중개자로서 검증후에, 생산자에게 생산 요청한다.
    public Reservation reserve(Theater theater, Movie movie, Screening screening, int count){
        // theater에게 예매권 발행(생성) 요청이 불가능한 경우 ( 인자별 -> 가능한경우 + !)
        // 1) 나와 계약된 theater인가
        // 2) theater에 등록된 정보가 맞는가 (생성자가 발행할 수 있는 정보인가)
        // 3) 이용가능한가 in 변동count필드를 가진 screening에서 확인
        if (!commissionRate.containsKey(theater)
            || !theater.isValidScreening(movie, screening)
            || !screening.hasSeat(count)) {
            return Reservation.NONE; // 데이터객체의 생성/조회는 검증실패후 null이 많아서 NULL객체를 가지고 있는다.
            // 외부에서는 if 반환받은 값 != NONE 일 때 진행한다.
        }

        final Reservation reservation = theater.reserve(movie, screening, count);
        //외부에서 [받아온 데이터객체]는 [NULL객체로 잘못된 데이터인지 아닌지 검사하고 로직을 수행한다.]
        // 예매권을 생성한 상태에서 -> seller에게 넘겨주기 전에,
        if (reservation != Reservation.NONE) {
            // 1) movie에 적용한 할인정책을 적용하여 전체 판매금(sales)을 계산 후, 거래조건rate를 적용해 commision을 챙긴다.
            final Money sales = movie.calculateFee(screening, count);
            final Money commission = sales.multi(commissionRate.get(theater));
            // 2) commission을 내 자본금에 보탠다.
            amount = amount.plus(commission);
            // 3) commisionn만큼 생산자에게서는 빼서, 판매금을 나눠준다.
            theater.plusAmount(sales.minus(commission));
        }

        // 커미션을 챙겨먹은 뒤, 예매권을 판매자에게 넘겨준다.
        return reservation;
    }
}
