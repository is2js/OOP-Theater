package theater;

public class Audience {
    private Ticket ticket = Ticket.EMPTY;

    private Invitation invitation = Invitation.EMPTY;

    private Long amount;

    public Audience(final Long amount) {
        this.amount = amount;
    }

    public void buyTicket(TicketSeller ticketSeller) {
        ticket = ticketSeller.getTicket(this);
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(final Invitation invitation) {
        this.invitation = invitation;
    }

    public void removeInvitation() {
        invitation = Invitation.EMPTY;
    }

    public boolean hasAmount(final Long ticketPrice) {
        return this.amount >= ticketPrice;
    }

    public boolean minusAmount(final Long ticketPrice) {
        if (ticketPrice > this.amount) {
            return false;
        }
        this.amount -= ticketPrice;
        return true;
    }
}
