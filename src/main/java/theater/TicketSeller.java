package theater;

public class TicketSeller {

    private TicketOffice ticketOffice;

    public void setTicketOffice(final TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public Ticket getTicket(Audience audience) {
        // if 상황에 따라 return할 객체가 Type은 동일하지만 여러개일 때, 제일 쉬운 상황의 return 객체를 deafult로 미리 생성해놓고
        // -> 상황별 변수를 재활용하면서, return 택1된 상황이 들어간 재활용 변수
        // -> early return이 아니라서 if else if로 상황만 바뀔 때 사용.
        //1) if 잘못된 티켓일 때,
        Ticket ticket = Ticket.EMPTY;
        //2-1)  초대권이 있을 때(수량0일 때, 반환되는 소모객체 = null객체를 이용)
        if( audience.getInvivation() != Invitation.EMPTY){
            // 2-1-1) (소속기관이자) 창고에서는 돈안주고 빼오기
            ticket = ticketOffice.getTicketWithNoFee();
            // 2-1-2) 빼온 티켓도 수량이 0이 아닐 때,
            if( ticket != Ticket.EMPTY){
                // 비로소 돈대신 -> 1회성 초대권을 제거하기
                audience.removeInvitation();
            }
        //2-2) (초대권은 없지만) 돈이 충분할 때
            // early return이 아니라면,
        } else if (audience.hasAmount( ticketOffice.getTicketPrice())) {
            //2-2-1) 창고에서 돈주고 빼오기
            ticket = ticketOffice.getTicketWithFee();
            //2-2-2) 빼온 티켓도 수량이 0이 아닐 때
            if(ticket != Ticket.EMPTY){
                // 비로소 돈 차감하기
                audience.minusAmount(ticketOffice.getTicketPrice());
            }
        }

        return ticket;
    }
}
