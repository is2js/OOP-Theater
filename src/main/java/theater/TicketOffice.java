package theater;

import java.util.ArrayList;
import java.util.List;

public class TicketOffice {

    private List<Ticket> tickets = new ArrayList<>();

    // 주는 객체(Theater)에 들어가서, 받는 기능을 제공해서, 재료객체를 받아온다.
    public void addTicket(final Ticket ticket) {
        //여러 개를 받아올 거면, 저장용 빈 list 필드를 초기화해서 add로 지속 받는다.
        this.tickets.add(ticket);
    }
}
