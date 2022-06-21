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

        //6. audience - 티켓구매 대신 Customer가 - 티켓예매를 한다
        // - 역시 자본금을 상태값으로 가진 체로 태어난다.
        final Customer customer = new Customer(Money.of(20000.0));

        //7. Customer가 예매를 한다.
        // - 예매도 사는 것이라, 갑은 파는놈 seller/ 입장을 검증하는 theater일 것이다.
        // - 사는 놈(을)은  갑들을 인자로 받되, 내부에서는 갑들이 제공하는 기능에 인자로 역으로 들어갈 것이다.
        // 7-1. 특정 theater에 저장된 영화들 중 1개 - 영화에 딸린 상영정보들 중 1개를 고른다.
        //       theater는 영화에 따른 상영정보들 함께 (map으로) 저장해놨으니 -> 영화1개를 key로 넣으면, 딸린 상영정보들을 제공해준다.
        // -> 특정theater, 특정movie를 위에서 생성한 것 똑같이 골랐다고 가정하고
        // --> 여러 상영정보 중 첫번째 것을 예매하기 위해, for로 꺼내서 돌되, + break;로 첫번째 것만 예매한다.
        // ----> 상위 - 중간 - 하위도메인 들 중 택1은 UI로 제공 되지만, 여기서는 for + break;를 활용해 여러개중 1개 택 한다.
        for (Screening screening: theater.getScreening(movie)) {
            // 7-2. 사는 사람은 을로서, 일단 팔고/검증하는 객체들을 인자로 받와 예매(구매)한다.
            //      무엇을 살것인지까지 인자로 받아오자.(ticket처럼 내부발행되는 것을 사는게 아님)
            customer.reserve( seller, theater, movie, screening, 2);
            // 7-3. 사는 놈이 제대로 샀는지 검증해주는 표 확인 theaer가 customer(사는 놈)과 검증할 정보를 인자로 받아 검증해준다.
            final boolean isOk = theater.enter(customer, 2);
            System.out.println("isOk = " + isOk);
            break; // 드랍박스 중 택1을 for+break;로 첫번째 것으로 대체한다.
        }
    }
}
