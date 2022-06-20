package theater;

public class Audience {
    public void setInvitation(final Invitation invitation) {
        throw new UnsupportedOperationException("Audience#setInvitation not write.");
    }

    public Ticket getTicket() {
        throw new UnsupportedOperationException("Audience#getTicket not write.");
    }

    public Invitation getInvivation() {
        throw new UnsupportedOperationException("Audience#getInvivation not write.");
    }

    public void removeInvitation() {
        throw new UnsupportedOperationException("Audience#removeInvitation not write.");
    }

    public boolean hasAmount(final Long ticketPrice) {
        throw new UnsupportedOperationException("Audience#hasAmount not write.");
    }

    public void minusAmount(final Long ticketPrice) {
        throw new UnsupportedOperationException("Audience#minusAmount not write.");
    }
}
