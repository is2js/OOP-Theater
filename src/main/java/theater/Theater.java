package theater;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Theater {

    public static final Set<Screening> EMPTY = new HashSet<>();
    private final Set<TicketOffice> ticketOffices = new HashSet<>();
    private final Map<Movie, Set<Screening>> movies = new HashMap<>();
    private Money amount;

    public Theater(final Money amount) {
        this.amount = amount;
    }

    public boolean addMovie(Movie movie){
        if (movies.containsKey(movie)) {
            return false;
        }
        movies.put(movie, new HashSet<>());
        return true;
    }

    public boolean addScreening(Movie movie, Screening screening){
        if (!movies.containsKey(movie)) {
            return false;
        }
        return movies.get(movie).add(screening);
    }

    public boolean contractTicketOffice(TicketOffice ticketOffice, Double rate){
        // 계약 갑인 ticketOffice가 먼저 계약성공여부를 결정해서 t/f를 알려준다.
        if (!ticketOffice.contract(this, rate)) {
            return false;
        }
        return ticketOffices.add(ticketOffice);
    }

    public boolean cancelTicketOffice(TicketOffice ticketOffice){
        //일단 DELETE에 해당하는 cancel은 존재여부부터 검증해야한다 + 갑이 허락해줘야한다.
        if (!ticketOffices.contains(ticketOffice)
            || !ticketOffice.cancel(this)) {
            return false;
        }
        return ticketOffices.remove(ticketOffice);
    }

    void plusAmount(Money amount){
        //값 객체는 연산후 새 객체를 반환하므로, VO 필드는 재할당 할 수 밖에 없다.
        // -> 스스로 값이 자동 증가하지 않으니 반드시 재할당 해줘야한다.
        this.amount = this.amount.plus(amount);
    }

    // 하위도메인 전체조회 by 상위도메인
    public Set<Screening> getScreening(Movie movie){
        //하위도메인의 조회는, 1) 상위도메인 존재여부 검증 + 2) 하위도메인이 자체가 비었을 경우 검증한다.
        // -> getter는 t/f가 아니기 때문에, 검증의 실패 결과로 매번 빈 컬렉션을 생성 반환하는 것보다는,
        // --> 상수로 만들어놓은 빈 EMTPY 컬렉션을 반환한다.
        if (!movies.containsKey(movie)
            || movies.get(movie).isEmpty()) {
            return EMPTY;
        }

        return movies.get(movie);
    }

    // movie-screening이 잘 매칭되는 것인지(상-하위도메인 연결 되는지) 확인
    public boolean isValidScreening(Movie movie, Screening screening){
        // movie가 존재여부 -> 그 movie에 하위도메인에 포함되는지 여부를 둘다 만족
        return movies.containsKey(movie) && movies.get(movie).contains(screening);
    }


    // 을로서 예매권을 구매한 Customer를 받아와, 내부정보로 검증해준다.
    // theater는 예매권을 최종 발행해주는 객체로서, 정보를 많이 가지고 있기 때문에, 여기서 검증해준다?!
    // -> 발행과 발행시 정보주입의 주체로서 검증의 책임이 있는 Theater
    // -> count(이용좌석수)는 theater가 가지고 있지 않아, 검증을 위해 외부에서 customer가 예매한 좌석수와 동일한 값을 받아서 입력한다.
    public boolean enter(Customer customer, int count) {
        // 1) customer에게 구매한 예매권을 받아온다.
        Reservation reservation = customer.reservation;
        // 2) 예매권 정보 vs theater내 정보를 비교한다. with 외부에서 입력해준 count
        //   -> 모두 유효한 정보여야지 true로 성공이다.
        return reservation != Reservation.NONE
            && reservation.theater == this
            && isValidScreening(reservation.movie, reservation.screening)
            && reservation.count == count;
    }

    // Customer로부터 seller -> office -> theater에게 정보 검증과 예매권 생성 요청
    // 검증
    Reservation reserve(Movie movie, Screening screening, int count) {
        // 예매권 생성 전 검증 1) 유효한 정보인지 2) 상영정보에 예약전 trigger메서드를 이용해서 빈 자리가 있는지 -> 검증실패시 데이터NULL객체 반환
        if (!isValidScreening(movie, screening)
            || !screening.hasSeat(count)) {
            return Reservation.NONE;
        }
        // trigger 성공 후 <-> 예매권발행 전, screening내 이용가능좌석수 field 차감 액션 발생
        screening.reserveSeat(count);
        return new Reservation(this, movie, screening, count);
    }

}
