package theater;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(final String[] args) {
        //1. 극장은 Ticket 발행에 필요한 정보인 fee를 Long 값 -> 값객체 Money를 사용하게 한다.
        final Theater theater = new Theater(Money.of(100.0));

        //2-1. 극장이 상영할 영화 객체를 만든다.
        // - 영화 객체는 제네릭을 통해, upperbound T형(추상형)의 구상형들 중 1개 구상형인 특정할인정책 아는 상태
        // -- 추상층이 T로 정의해서 가능한 것 ( or 익클의 분신술을 만든 자신이 추상클래스가 되면서 T로 정의)
        // - 영화를 만들 때 필요한 정보는 1)영화제목 2) 지속시간 3) 가격 4) 순서 & 원금할인 정책 등이다.
        Movie movie = new Movie<AmountDiscount> (
            "spiderman",
            Duration.ofMinutes(120L),
            Money.of(5000.0),
            new SequenceAmountDiscount(Money.of(1000.0), 1)
        );
        //2-2.  극장은 만들어진 영화객체를 client에서 받아간다.
        // - setter는 1개 받기 기능 / add는 2개이상 받기기능
        theater.addMovie(movie);
    }
}
