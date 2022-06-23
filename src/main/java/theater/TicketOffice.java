package theater;

public class TicketOffice {

    private Money amount;
    public TicketOffice(Money amount) {
        this.amount = amount;
    }

    public boolean contract(final Theater theater, final Double rate) {
        throw new UnsupportedOperationException("TicketOffice#contract not write.");
    }

    public boolean cancel(final Theater theater) {
        throw new UnsupportedOperationException("TicketOffice#cancel not write.");
    }
}
