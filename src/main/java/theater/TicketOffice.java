package theater;

import java.util.ArrayList;
import java.util.List;

public class TicketOffice {

    private Money amount;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketOffice(Money amount) {
        this.amount = amount;
    }

    public void addTicket(final Ticket ticket) {
        this.tickets.add(ticket);
    }

    public Long getTicketPrice() {
        if (tickets.size() == 0) {
            return 0L;
        }
        else {
            return tickets.get(0).getFee();
        }
    }

    public Ticket getTicketWithFee() {
        if (tickets.size() == 0) {
            return Ticket.EMPTY;
        }

        final Ticket ticket = tickets.remove(0);
        amount += ticket.getFee();
        return ticket;
    }

    public Ticket getTicketWithNoFee() {
        if (tickets.size() == 0) {
            return Ticket.EMPTY;
        }
        final Ticket ticket = tickets.remove(0);
        return ticket;
    }
}
