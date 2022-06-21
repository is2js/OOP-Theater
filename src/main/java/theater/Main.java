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


        //3. 각 극장(상위1) 객체에는 영화들(하위N) 을 [매칭하여 저장]할 뿐만 아니라
        //   각 영화들에 딸린 상영정보(Screening)들도 넣어줘야하는데,
        //   각 영화당 상영정보들을 매칭해서 넣어줘야하니, pk에 대한 fk처럼, pk movie와 같이 넣어줘야 매칭된다.
        // -> theater screening 객체(순서, 시간, 가용좌석수)) 받기기능
        for (int day = 7; day < 32; day++) {
            for (int hour = 10, seq = 1; hour < 24; hour +=3, seq++) {
                theater.addScreening(
                    movie, //fk
                    new Screening(
                        seq,
                        LocalDateTime.of(2019, 7, day, hour, 00, 00),
                        100
                    )
                );
            }
        }

        //4-1. 티켓판매소를 만든다. 얼마나 판매했는지 자본금을 가지고 초기화하되 값객체를 쓴다.
        // Long -> Money(double)
        final TicketOffice ticketOffice = new TicketOffice(Money.of(0.0));
        //4-2. theater와 협력해서 티켓을 팔아줄 티켓판매소는 [서로 계약]을 맺되, [수수로fee]도 같이 받아와서 계약한다.
        // - 계약시, 계약할 객체 + 계약조건도 같이 인자로 받음.
        theater.contractTicketOffice(ticketOffice, 10.0);

        //5. seller는 프리랜서로서, 소속기관 null상태로 생성된 뒤에, 바뀔 수 있는 소속기간을 배정한다.
        final TicketSeller ticketSeller = new TicketSeller();
        // 계약이 아니라고 치고, 상위도메인으로서 소속기관 office를 받기기능을 통해, 소속기관 정보필드에 꽂아넎는다.
        ticketSeller.setTicketOffice(ticketOffice);
    }
}
