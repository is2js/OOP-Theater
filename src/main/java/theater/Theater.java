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

    public boolean enter(Audience audience) {
        Ticket ticket = audience.getTicket();
        return ticket.isValid(this);
    }
}
