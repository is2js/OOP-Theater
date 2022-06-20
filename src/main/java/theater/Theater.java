package theater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Theater {

    private final Long fee;
    private final List<TicketOffice> ticketOffices = new ArrayList<>();

    public Theater(final Long fee) {
        this.fee = fee;
    }

    public Long getFee() {
        return fee;
    }

    // 외부에서 거래할 office들 목록을 받아와 필드에 상태값으로 저장해놓는다.
    public void setTicketOffices(TicketOffice... ticketOffices) {
        this.ticketOffices.addAll(Arrays.asList(ticketOffices));
    }

    // 거래하는 office인 경우, num개의 ticket을 내부에서 1개씩 생성하면서 준다.
    public void setTicket(TicketOffice ticketOffice, Long num) {
        if (!ticketOffices.contains(ticketOffice)) {
            return;
        }
        while (num-- > 0) {
            ticketOffice.addTicket(new Ticket(this));
        }
    }

    public void setInvitation(Audience audience) {
        audience.setInvitation(new Invitation(this));
    }

    // 입장시, 표가 있나 없나 확인하는 기능
    public boolean enter(Audience audience) {
        Ticket ticket = audience.getTicket();
        // 티켓의 확인은, 티켓의 정보가 옳은지 확인할 길은, 티켓에 정보를 제공해준 this(현재 theater instance)이다.
        return ticket.isValid(this);
    }
}
