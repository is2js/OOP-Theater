package theater;

public class Ticket {
    private final Theater theater;

    // 발행되는 객체는, 발행주체를 생성할 때부터 알고 있으며, 발급주체가 불변하도록 final field로 박아둔다.
    public Ticket(final Theater theater) {
        this.theater = theater;
    }

    public boolean isValid(final Theater theater) {
        throw new UnsupportedOperationException("Ticket#isValid not write.");
    }

    // 정보를 값이 아닌 식별자인 객체로 받아와 저장해놨다면, 정보를 내어줄 때 포인터의 포인터로 내어준다.
    public Long getFee() {
        return theater.getFee();
    }
}
