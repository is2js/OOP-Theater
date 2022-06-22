package theater;

import java.util.ArrayList;
import java.util.List;

public class Theater {

    private final Money amount;
    private final List<TicketOffice> ticketOffices = new ArrayList<>();

    public Theater(final Money amount) {
        this.amount = amount;
    }

    public boolean enter(Audience audience) {
        Ticket ticket = audience.getTicket();
        return ticket.isValid(this);
    }
}
