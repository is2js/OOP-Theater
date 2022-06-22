package theater;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Theater {

    public static final Set<Screening> EMPTY = new HashSet<>();
    private final Set<TicketOffice> ticketOffices = new HashSet<>();
    private final Map<Movie, Set<Screening>> movies = new HashMap<>();
    private final Money amount;

    public Theater(final Money amount) {
        this.amount = amount;
    }

    public boolean enter(Audience audience) {
        Ticket ticket = audience.getTicket();
        return ticket.isValid(this);
    }
}
