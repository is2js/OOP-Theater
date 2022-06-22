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

    public boolean enter(Audience audience) {
        Ticket ticket = audience.getTicket();
        return ticket.isValid(this);
    }
}
