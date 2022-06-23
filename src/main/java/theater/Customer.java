package theater;

public class Customer {

    // 데이터 객체를, 태어난 이후 받는다면(구매), 미리 NULL객체로 초기화해서 상대방 확인시, NULL객체로 확인하게 한다.
    public Reservation reservation = Reservation.NONE;
    private Money amount; // VO필드는 재할당이 운명이라 not final이다.

    public Customer(final Money amount) {
        this.amount = amount;
    }

    // 거래(예매) 요청 -> 갑에 의해 물건을 받아와 자신이 가지니 setter역할로서 void로 정의한다
    public void reserve(TicketSeller ticketSeller, Theater theater, Movie movie, Screening screening, int count){
        reservation = ticketSeller.reserve(this, theater, movie, screening, count);
    }

    public boolean hasAmount(final Money amount) {
        return this.amount.greaterThan(amount);
    }

    public void minusAmount(Money amount){
        this.amount = this.amount.minus(amount);
    }
}
