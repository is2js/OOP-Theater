package theater;

public class Main {
    public static void main(final String[] args) {
        //1. 극장은 Ticket 발행에 필요한 정보인 fee를 Long 값 -> 값객체 Money를 사용하게 한다.
        final Theater theater = new Theater(Money.of(100.0));
    }
}
