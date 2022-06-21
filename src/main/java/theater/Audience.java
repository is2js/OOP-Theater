package theater;

public class Audience {
    private Ticket ticket = Ticket.EMPTY;
    private Invitation invitation = Invitation.EMPTY;
    private final Long amount;

    public Audience(final Long amount) {
        this.amount = amount;
    }

    // 고객의 사는 기능은 파는놈을 인자로 받아서 물건을 받아 -> 상태값으로 저장한다.
    // -> 파는 놈의 파는 기능을 이용하며, 가져온 물건은 상태값으로 저장하여 void 메서드다.
    // -> 소모성 재료객체를 상태field(not final)로 가지고 있는다면,  default 값은 null객체로 상태값을 준다.
    public void buyTicket(TicketSeller ticketSeller){
        ticket = ticketSeller.getTicket(this);
    }



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
