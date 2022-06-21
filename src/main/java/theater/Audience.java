package theater;

public class Audience {
    private Ticket ticket = Ticket.EMPTY;

    private Invitation invitation = Invitation.EMPTY;

    private Long amount;

    public Audience(final Long amount) {
        this.amount = amount;
    }

    // 고객의 사는 기능은 파는놈을 인자로 받아서 물건을 받아 -> 상태값으로 저장한다.
    // -> 파는 놈의 파는 기능을 이용하며, 가져온 물건은 상태값으로 저장하여 void 메서드다.
    // -> 소모성 재료객체를 상태field(not final)로 가지고 있는다면,  default 값은 null객체로 상태값을 준다.
    public void buyTicket(TicketSeller ticketSeller) {
        ticket = ticketSeller.getTicket(this);
    }

    // 내 정보(상태값)만 주기기능 getter는, 상대방(Theater)이 내 밖에서 직접 나를 검증/판단할때를 준다.
    // -> 나의 정보를 가져가서 판단하는 것보다, 나에게 시키는게 더 좋을 듯하다.
    public Ticket getTicket() {
        return ticket;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    // setter는 필드로 스스로 받기기능이다. -> 객체필드를 받는다면 미리 알아야한다.
    public void setInvitation(final Invitation invitation) {
        this.invitation = invitation;
    }

    public void removeInvitation() {
        // 1회성 소모재료객체의 소진은 0 대신 null객체를 사용한다.
        invitation = Invitation.EMPTY;
    }

    // 물건을 사는놈은 을로서, [파는놈]에게 거래가능한지 [거래가능 검증당하기 기능]을 제공해야한다.
    // -> 주는 기능이 아니라서 기능제공해도 을임.
    public boolean hasAmount(final Long ticketPrice) {
        return this.amount >= ticketPrice;
    }

    // 물건을 사는놈은 을로서, [파는놈]에게 물건양호시 [돈차감 후 성공여부]기능을 제공해야한다.
    // -> 차감이 실패할 수 도 있으니, 차감성공만 true를 반환하는 boolean메서드다.
    public boolean minusAmount(final Long ticketPrice) {
        if (ticketPrice > this.amount) {
            return false;
        }
        this.amount -= ticketPrice;
        return true;
    }
}
