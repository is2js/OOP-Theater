package theater;

public class Ticket {
    private static final Ticket EMPTY = new Ticket(null);
    private final Theater theater;
    private boolean isEntered = false;

    public Ticket(final Theater theater) {
        this.theater = theater;
    }

    // 유효한표인지 검증할 때 필요한 정보도, 객체로 받아온다.
    public boolean isValid(final Theater theater) {
        // 검증시 1) 이미 쓴표냐? 2) 현재 주체가 발행주체랑 다르냐? 3) 잘못생성된 표냐? null객체
        if (isEntered || theater != this.theater || this == EMPTY) {
            return false;
        }
        // 잘못된 예들의 검증을 통과하면, 이미 쓴표로 toggle 먼저하고, true를 반환
        isEntered = true; // 상태관리(flag toggle)를 자기가 하는 maintenance of status
        return true;
    }

    public Long getFee() {
        return theater.getFee();
    }
}
