package theater;

public class Main {
    public static void main(final String[] args) {
        // 1-1. 발행할 ticket 가격정보를 가진 체로 극장 생성
        // - 아직 같이 일할 티켓판매소 등록(list필드)은 안되어있음.
        final Theater theater = new Theater(100L);
        // 1-2. 자신의 돈을 가진체로 고객 생성
        final Audience audience1 = new Audience(0L);
        final Audience audience2 = new Audience(50L);
        // 1-3. 판매금액을 0원으로 생성되는 티켓판매소
        // -> theater가 일방적으로 알고, 판매소는 theater를 모름.
        final TicketOffice ticketOffice = new TicketOffice(0L);
        // 1-4. 최초 무소속(setter:프리랜서 소속기관)으로 생성되는 판매원
        final TicketSeller ticketSeller = new TicketSeller();

        //2-1. theater에 협력할 티켓판매소 정보를 저장한다. for 확인용
        theater.setTicketOffices(ticketOffice);
        //2-2. theater가 ticket을 발행할 때는, 저장된 협력 티켓판매소를 데리고와서 제한해서 갯수만큼 발행하여, 받기기능으로 받아가라고 한다.
        // -> 내부에서 발행한다면, 받을놈을 인자로 받아서, 받기기능으로 받아가라고 한다.
        theater.setTicket(ticketOffice, 10L);
        //2-3. theater는 ticket외에 invitation을 내부에서 발행하여 고객에게 받아가라고 한다.
        // -> 내부에서 발행한다면, 받을놈을 인자로 받아서, 받기기능으로 받아가라고 한다.
        theater.setInvitation(audience1);

        //3. seller는 변동가능성이 있는 소속기관 정보를 나중에 정해서(프리랜서) 받는다.
        ticketSeller.setTicketOffice(ticketOffice);

        //4. 고객(사는놈)은 seller를 받아서, 티켓을 산다.
        // -> 파는놈이 갑으로서, 내부에서는 파는놈이 고객(사는놈)을 인자로 받아, 파는 기능을 제공한다.
        audience1.buyTicket(ticketSeller);
        audience2.buyTicket(ticketSeller);

        //5. theater는 ticket을 산 고객들이, 입장가는 한지 검증한다.
        // -> 둘다 돈이 모자라지만, 고객1은 theater가 invitation을 줬기 때문에 입장ok가 뜰 것이다.
        final boolean isOk1 = theater.enter(audience1);
        final boolean isOk2 = theater.enter(audience2);
        System.out.println("isOk1 = " + isOk1);
        System.out.println("isOk2 = " + isOk2);
    }
}
