package theater;

import java.util.HashMap;
import java.util.Map;

public class TicketOffice {

    private Money amount;
    private final Map<Theater, Double> commissionRate = new HashMap<>();
    public TicketOffice(Money amount) {
        this.amount = amount;
    }

    // 계약: 거래theater + 거래조건 저장하기
    // -> 내부에서 처리되며, 성공/실패를 boolean으로 알려주어, 갑 저장성공시 -> 을 저장하는 순서로 하게 한다.
    public boolean contract(final Theater theater, final Double rate) {
        // 저장할 땐, 상위개념 중복여부부터 검사한다.
        if (commissionRate.containsKey(theater)) {
            return false;
        }

        commissionRate.put(theater, rate);
        return true;
    }
    // 계약취소 -> 삭제개념으로 존재여부 검사 + 내부처리로서 t/f반환
    // -> 계약취소도 상대방간 이루어져야하는데, 갑(office)부터 remove하고 t/f를 받아 -> 을(theater)가 remove한다.
    public boolean cancel(Theater theater) {
        if (!commissionRate.containsKey(theater)) {
            return false;
        }
        commissionRate.remove(theater);
        return true;
    }
}
