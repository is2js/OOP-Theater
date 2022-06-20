package theater;

import java.util.ArrayList;
import java.util.List;

public class TicketOffice {

    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketOffice(Long amount) {
        this.amount = amount;
    }

    // 외부가 알아서, 받는 기능.(Theater -> Ticketoffice)
    // - 1:N으로 아는 상황이니, offices들을 돌면서 받아감.
    public void addTicket(final Ticket ticket) {
        this.tickets.add(ticket);
    }

    // 외부가 알아서 빼가지만, 빼가기 전에 먼저, 객체 정보를 조회하는 기능도 제공해준다.
    public Long getTicketPrice() {
        // 소모성 재료객체는 수량을 확인한다.
        if (tickets.size() == 0) {
            return 0L;
        }
        else {
            return tickets.get(0).getFee();
        }
    }

    // 외부가 알아서, 빼가는 기능
    //1) 일반 audience를 만난 seller가 창고에서 빼감
    public Ticket getTicketWithFee() {
        // 1) 창고에서 빼갈 때, 소모되어 수량제한이 있는 재료객체는, 갯수가 0이면 null객체를 반환한다.
        if (tickets.size() == 0) {
            return Ticket.EMPTY;
        }
        // 2) 창고에서 빼가는 상황일 때, 빼간 만큼 1개씩 remove 와 그 판매비용을 챙긴다.
        //
        final Ticket ticket = tickets.remove(0);
        amount += ticket.getFee();
        return ticket;
    }

    //2) invitation을 가진 audience를 만난 seller가 창고에서 빼감
    public Ticket getTicketWithNoFee() {
        // 1) 창고에서 빼갈 때, 소모되는 것은 갯수 체크후 0개면 null객체 반환
        if (tickets.size() == 0) {
            return Ticket.EMPTY;
        }
        final Ticket ticket = tickets.remove(0);
        return ticket;
    }
}
