package theater;

public class Customer {

    // 데이터 객체를, 태어난 이후 받는다면(구매), 미리 NULL객체로 초기화해서 상대방 확인시, NULL객체로 확인하게 한다.
    public Reservation reservation = Reservation.NONE;
    private Money amount; // VO필드는 재할당이 운명이라 not final이다.

    public Customer(final Money amount) {
        this.amount = amount;
    }

    public boolean hasAmount(final Money price) {
        throw new UnsupportedOperationException("Customer#hasAmount not write.");
    }
}
