### Description
- object book의 Theater 예제의 상호의존성을 수정한 설계도대로 객체class를 하나씩 만들어간다.
- `step1` : Client코드부터 짜야하지만, 상호관계를 파악하기 위해 class들을 순서대로 설계한다.

### STEP1
- theater 수정 설계도대로 class 설계하기
    - 객체 상호의존표
        ![image-20220122214556326](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220122214556326.png)
    - Diagram
        ![image-20220621120453611](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621120453611.png)

### STEP2
- theater를 예매권 구입으로 변경
    - 객체 상호의존표
      ![image-20191124163632027](https://raw.githubusercontent.com/is3js/screenshots/main/68747470733a2f2f747661312e73696e61696d672e636e2f6c617267652f30303679386d4e366779316739393633666b337a766a333131713069676d79372e6a7067)
    - Diagram
      ![image-20220623170546098](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623170546098.png)
      ![image-20220623170509341](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623170509341.png)

### STEP2 과정

- [최종코드](https://github.com/LenKIM/object-book/tree/master/object2/src)

- step1의 모델

  ![image-20191124163632027](https://raw.githubusercontent.com/is3js/screenshots/main/68747470733a2f2f747661312e73696e61696d672e636e2f6c617267652f30303679386d4e366779316739393633666b337a766a333131713069676d79372e6a7067)



![image-20220623170546098](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623170546098.png)

![image-20220623170509341](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623170509341.png)

- theater가 재료객체 **ticket, invitation을 내부에서 발행(생성)**하였는데,  그 이유는 **발행시 theater를 생성자의 인자로 받아가 정보를 필드에 저장**하도록 내부에서 넘겨줬기 때문이다.
- theater가 발행한 **ticket 판매의 복잡한 책임**은, 책임(메서드)이 커지기 때문에 직접 하지 않고, **ticketOffice에게 가져가게 위임**했었다.
    - ticketOffice에게 와서 받아가라고 하여, 발행과 동시에 넘겨줬다.
    - ticketOffice는 ticketSeller를 통해 판만큼의 가진 티켓은 차감 + 그만큼 Fee를 얻었다.
- ticketOffice는 티켓 창고역할만 하고, 실제 판매는 프리랜서인 ticketSeller를 고용(하위도메인으로서 소속기관Office를 필드로 가짐)하여, Seller가 판매를 한다.



- step2의 새로운 모델(책)
  ![image-20220126224317186](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220126224317186.png)

    - **Fee <-> Amount의 판매(물물교환)로직이 사라진다.**
        - invitation도 사라짐
        - 대신 reservation을 하게 한다.
    - **책**에서는 Screening이라는 상영정보 객체가 `.reserveSeat()`을 예약 책임을 가지고 있다.
        - `정보전문가 패턴`에 의해 정보를 가장 많이 가진 객체가 책임을 지는 구조이다 -> **말이 안된다.**
        - 상영정보는 real domain에서는 자리번호/순서/시간을 가진 단순 시간표정보일 뿐이다.
        - 게다가 현실에서는 1개의 상영정보(1 Screening)는 여러 영화(N Movie) 가지는 구조를 가진다.
            - 10시 - 스타워즈
            - 10시 - 인디아나존스
            - 10시 - 아이언맨
            - **영화들은 1개의 상영정보만 알아서 fk로 참조하면 되는 것이다.**
            - **N을 1의 필드로 넣으면 복잡해진다.**
                - 상영정보에 movie제목을 넣지말자.
                - 일부만 다른 값을 가져 중복 문제가 발생할 때, 다른 값을 가지는 그 필드(영화제목)은 정규화 대상으로서 N table로 옮겨야함.
        - **그렇다면, 상영정보는 movie 제목도 없어야하고, 시간만 가지고 있어야한다 -> 예약을 스스로 할 수 없다.**
            - 현실에서는 여러개의 극장 : 극장마다 여러개의 영화 :그 영화마다 여러개의 상영정보 형태를 가짐.
    - **책**에서는 할인 정책도 소개한다.
        - **어떻게** 할인하는 가 -> Discount**Policy**
            - AmountDiscountPolicy: 돈을 깍아서
            - PercentDiscountPolicy: 퍼센트로 깍아서
        - **언제** 할인하는 가 -> Discount**Condition**
            - TimeDiscountCondition: 시간이 언제인지 기준으로 할인
            - SequenceDiscountCondition: 몇번째 상영인지를 기준으로 할인
        - 책에서 소개한 것 처럼 **연관관계가 수직관계로 할인정책이 나오진 않는다.**
            - 각 정책들은 교차조건이다. 서로간의 부모 자식간은 상관없다.



##### Main

- 객체 설계시 Clinet코드부터 시나리오에 맞춰 짜야한다.

  ![image-20220127212251961](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220127212251961.png)
  ![image-20220127220145163](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220127220145163.png)



1. client에서 극장객체를 만드는데, Theater은 Ticket 발행에 필요한 정보인 fee를 Long 값 -> 값 객체 Money를 사용하게 한다. (책에서 요구함)

    - 값 객체는 일반 참조형과는 다른 성질을 가지고 있다.
      ![image-20220621182630624](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621182630624.png)

2. client에서 영화객체를 만드는데,

    - 제네릭으로 upperbound T형(추상형)의 구상형들 중 1개 구상형인 특정할인정책 아는 상태의 영화이다.

        - **왜??**
          ![image-20220621204507969](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621204507969.png)
        - **책에서 `영화`는 `무조건` DiscountPolicy 2개(Percent or Amount DiscountPolicy) 중에서 `1개의 policy는 movie에 필수 정책 적용`해야하는 `제약조건`을 주었다.**
            - **`1:1제약조건`의 적용은 값으로 적용X /  `생성자에서 객체로 주입하여 적용 X` / `제네릭`을 통해,  추상체로 정의된 정책을 `<형>으로 1:1 제약조건을 적용한 SubType`을 만들어낼 수 있기 때문이다.**
                - 참고) `FrontEnd <Client> ` `FrontEnd<ServerClient`>의 경우
                    - 1:1 제약조건이 아니라 **들어오는 구상체마다 구현이 다른데, 여러 구상체를 받아야할 경우 추클->익클 분신술 SubType으로 `1개의 구상체가 여러 특정형을 상황에 맞게 SubType을 만들어내야하는 상황`이였음.**
                    - **근데, 현재 구상체Movie에 타 추상체(DiscountPolicy)의 구상체들을  택 1일해서 적용하려면, 인자로 받더라도, 그 구현체에 따라 다르게 작동한다. 더이상 구상화할 수 없는 Movie에 대해 N개의 추상체가 택1로 들어와야한다면 instanceof로 확인해서 쓸 수 밖에 없을 것이다.**
                    - **`my) 구상불가능한 최하위 구상체` vs `N개의 구상체 택1`의 `1:1매칭`은 `instanceof`와 마찬가지로서, 제네릭으로 적용한다.**
                        - 여러 구상체가 매번 인자로 들어와도, 추상체로 들어올거라, instanceof 써서 그에 맞게 적용하는 수 밖에 없을 것이다.
            - 이 때, Movie는 더이상 구상형Movie가 아니라 **Movie를 추상층으로 한 `Movie<특정형>`의 `Movie의  Subtype Movie`이며 Movie보다 더 구상형이 된다.**
            - Movie는 SubType을 가질 수 있는 Class라고 인식해야한다.

    - Movie의 제네릭을 통해 `1:1 제약조건 적용된 SubType`이 정해졌으면, 4개의 정보를 받아서 생성한다.

        - 3) 실제로 영화가격은, 바로 안정해지고, 상영정보(Screening)에 있는 상영시간에 의해서 차후 정해진다.
        - 4) **AmountDiscount의 `1:1 필수 제약조건의 policy`가 적용된 SubType Moive 상태에서, SequenceAmountDiscount라는 더 구체적인 when?의  `condition policy의 추가 정책`은 `먼저 지정된 제약조건policy Type에 맞게 + 생성자 인자에 객체로 주입해서 정책 적용`하였다.**
            - PercentDiscount
                - ??
                - ??
            - AmountDiscount
                - **Sequnece**AmountDiscount
                - ???AmountDiscount
            - condition policy(when)의 적용을 인자로 하려면, **`제네릭`으로 먼저 적용된 `제약조건 policy`에  맞게, 객체로 적용한다.**

    - 생성된 영화는 theater가 받기기능으로 받아가 저장한다.

        - **각 극장(1)마다 걸릴 영화(N)에 대해 `매칭을 받기기능으로 1쪽에 저장`하였다.**
            - **각 영화(1)마다 걸릴 상영정보(N)또한 `극장 안에다가 매칭`시켜줘야한다.**

      ![image-20220621182334082](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621182334082.png)

3. theater에 생성된 영화에 딸린 `상영정보들(Screening)`도 넣어 저장한다.

    - 하위도메인으로서, 상위도메인인 영화_id(fk) 대신 영화객체를 매번 넣어준다.

    - 상영정보에 예매제한이 있어야하는데, **원래는 상영관을 사용하나, 상영관 대신 가용좌석수를 통해 무한예매를 방지**한다.

      ![image-20220621203224750](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621203224750.png)

    - 여러개를 넣을 것이므로 변하는 변수들은 변수로 지정한 뒤,  밖에서 반복문을 돌린다.

        - seq,  hour은 같이 같은 제한갯수로 도니까, 3시간마다 돌리면서 24시전까지의 제한이 있는 hour에만 제한을 주고, 안쪽에서 같이 돌려준다
        - 매번 하루의 제한이 끝날때마다 day를 바깥에서 돌려준다. 8일부터 31일까지 돌릴 예정이다.

      ![439fc601-00e5-4049-9657-825a7c602880](https://raw.githubusercontent.com/is3js/screenshots/main/439fc601-00e5-4049-9657-825a7c602880.gif)

4. theater와 협력할  ticketoffice와 **계약**을 맺되, **office에게 떼줄 수수료(fee)인 계약조건도 같이 인자로 받아서계약한다.**

    - **기존**에는 티켓을 팔아줄 **협력 ticketOffice들을 받아와서 setter로 나에게 꽂아넣어서 리스트만 저장했었다**

      ```java
      theater.setTicketOffices(ticketOffice);
      ```

    - **지금은 서로 알아서 계약을 한다.**

        - **계약시 들어가는 수수료10.0도 사실 `객체로 메세지`를 보내야한다. 인자에 값을 쓰면 객체지향 위반이다.**

      ![image-20220621210008036](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621210008036.png)

5. seller는 main코드 변화가 없다.

   ![image-20220621210742699](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621210742699.png)

6. audience - buyTicket 대신 **Customer가 상태값(변하는 변수)으로 자본금을 가지고 태어나, `reserveTicket()`의 예매를 하도록 바뀌었다.**

   ![image-20220621211156036](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621211156036.png)

7. theater가 client코드 -> 외부 -> 나중에는 UI로 **저장하고 있는 영화 / 상영정보드을 보여줘야할 책임이 있기 때문에 Main에서 getter(주기)기능을 제공한다.**

    - for + break;로 첫번째 것만 선택했다고 가정하고 한다.

    - **Customer는 스스로 예매`.reserve()`를 하지만,  사는 놈은 을의 위치이다. 가지고 있어 파는 놈(`seller`)와 산 것을 검증하는 `theater`의 을 인자로 받아서 내부에서는 그들의 기능을 이용한다.**

        - 구매할 때, Ticket 1개만 구매하는 것이 아니라, **어떤 영화의 어떤 상영정보를 골랐는지 `살 재료객체`도 같이 인자로 받는다. 추가로 `제한을 확인해야하는 구매 수량`도 인자로 받는다.**
        - reverse(): 예매
            - 특정 seller에게
            - 이 theater의
            - 특정 movie를
            - screening일 때
            - 2명이서
                - 보고 싶다고 예약을 한다.

    - Customer는 **내부에서 파는놈/산것 검증하는 놈을 `인자로 받아서 이용하는 이유`는 `산 것을 반환받아 내부 필드에 꽂아넣기 위해` + `산 것을 검증하기 위해`서 갑들을 인자로 받는다. 그외에 `구매수량`도 들어간다.**

        - **내부에서 갑인 thater가** theater.enter ( customer )로 **예매 성공한 customer의 예매 정보를 검증한다**
        - **예매좌석수 2도 객체지향 위반이다. 실제론 `SeatCount.of(2)`가 들어가야할 듯**
        - **isOk의 불린형도 원시값이라 객체지향 위반이다. 객체로return되어야한다.**
            - 위에 반복문으로 돌악는 seq도 **`SequenceNumber.of(seq)`의 객체로 들어가야한다.**
            - **영화제목도 `Title.of()`로 객체로 들어가야한다.**

      ![image-20220621215701031](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621215701031.png)



8. 원시값을 메세지로 넘기면 안된다. **`객체`만이 역할을 수행한다. `Type`만이 책임을 수행한다.**
    - 가능좌석수를 100으로 확신하고 썼어도... 확장가능성이 없다고 확신하면 안된다. 중간에 바뀔 수 있으면, 다른데 전파를 제대로 하기 위해서는 `객체껍데기`로 한번 쏴서 **포인터(변수)의 포인터(갖다 쓴놈)이 대응을 할 수 있다.**



##### DiscountCondition

- 일단 책에서 나오는 수직구조의 policy연관관계는 틀렸다고 한다.

  ![image-20220129001023447](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220129001023447.png)



- **Condition Policy**는 **최소화된 2가지 `역할`을 `공통`적으로 가지는 인터페이스다.**

    - my) 추상메서드는 공통을 모은 것이므로 책임(메서드) -> `역할`(추상메서드)이라고 승격해서 불러야한다.
        - **아래 2가지 역할은 iterator패턴과 동일하다.**
            - hasNext -> next() 보다는 **2가지 역할이 중요**

    1. **조건을 만족하는지 `액션 발동조건 검증` 역할**
        - `isSatisfiedBy()` 여기선 상영정보객체  + 관객수를 받음
    2. **`조건 검증 통과시 할 정책적용 액션`역할**
        - 여기서는 `calculateFee( )`로  discount 적용 액션

  ![image-20220621224203970](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621224203970.png)



- **외부 조건에 의해 변화/발동하는 경우, 2가지 인터페이스를 가져야한다.**
    1. 발동조건 검증 메서드(trigger) : **is~ has~ (외부조건 파라미터)**
    2. 액션 메서드(action): **발동(외부조건 파라미터)**
        - `DiscountCondition`에서도 있고
            - **외부 조건에 따라 `정책 발동`**
        - `Screening`의 seat필드에 대해서도 사용례가 있음.
            - **외부 조건에 따라 `필드변화`**



- 인터페이스
    - 제일 좋은 것은 마커 인터페이스
    - **책임/역할이 1개인 인터페이스**가 2번째로 좋다.
    - 여기선 역할이 2개인데??
        - 인터페이스가 충분히  추상화되지 않았다.
        - **2개 정도도 책에선 만족한다. 메서드를 1개로 하면, layer가 그만큼 깊어지고, 이해하기 힘들 수 있다. 너무 깊은 추상화는 팀원들의 화를 불어일으킬 수 있음.**

- 메세지
    - 인자가 없는 함수 = 프로시져 = 혼자서 스스로 책임 수행
        - 결합이 약해지는 단점.
        - 우리 수준에서 못짠다는 단점
    - **인자가 1개인 함수**
        - 2번째로 좋은 함수로서 우리가 짜야하는 함수
    - 어떻게 좋은 함수(인자 1개)를 짤 수 있을까?
        - 여러개의 인자들을 객체화 시켜서 **객체 1개로 전달한다. `언제나 옳은 객체지향 메세지`가 된다.**
    - 인자가 2개이상이다?
        - **메세지가 충분히 `추상화, 객체화, 형`이 되지 않았다.**



##### DiscountPolicy

- 책에서는 enum으로 적용하지만, **enum은 형Type이 될 수없다.** (제네릭도 적용 불가?!)

- **policy 자체는 `마커` 인터페이스이지만, 그 내부에 `policy종류별`로  `policy 마커 인터페이스를 상속한 인터페이스`로 정의**한다

  ![image-20220202213429164](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220202213429164.png)

    - inner interface의 정의는.. 부모interface를 중괄호로 구현해서 상속시켜야한다.
    - policy 자식의 종류에는 NONE policy도 포함시킨다.

  ![image-20220621225931356](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621225931356.png)

    - 차후 자식 policy는 아래와 같이 적용할 예정이다.

      ![image-20220621232928479](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621232928479.png)



- **차후 상세 정책은**

    1. policy 자식 중 1개를 구현하면서

    2. condition policy(중간추상층, 1개의 메서드를 중간에 미리 구현)를 상속한

    3. 구상class를 만들어 쓸 예정이다.

       ![image-20220621233211776](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621233211776.png)



##### Condition -> Policy 순으로 정책 적용해보기(시행착오)

- DiscountCondition은 **어쩔 수 없이 2개의 메소드를 가진다.**
    - **구상체를 추상클래스(중간 추상층)로 만들고 `발동조건 메서드` 1개를 중간에서 막고, `액션`메서드는, 2번째 정책(마커인터페이스)를 구현하되, 개념을 액션메서드에 녹여서 구현한다.**
        - class는 내려온 2개의 추상메서드들을 반드시 개별구현해야하지만
        - abstract class는 **상위추상층의 추상메서드들을 `중간 추상층으로서 중간에 @Override개별구현하여 fix`해놓으면 최종 구상class들은 `안보이지만, 갖다쓰는 물려받은 메서드`가 된다. 많은 추상메서드들 중에 `선택해서 중간구현`할 수 있다.**
    - **my) 추상클래스 사용례**
        - 전략패턴: 구상체들의 개별구현을 자신의 분신술로 Main으로 끝까지 미룰 때
        - frontEnd: 최하위구상층이 여러특정형을 알아야하고 다 개별구현해야할 때 제네릭+분신술 쓸 때
        - movie : 여러특정형(policy)과 필수로 1:1매칭되어 적용되어야할 때
        - DiscountCondition:  인터페이스의 2개 메소드 중 1개를 중간추상층으로 구현해야할 때
- **2개의 메소드를 가진 Condition Policy 인터페이스 처리 전략**
    - 원래는 특정형의 구상체class가 2개의 메소드를 구현
    - **특정형 기반으로 제한(추상클래스로 받을 기존 구상형)**하여, **추상클래스 중간추상층으로 만들어, 1개의 메소드만 중간에서 선택구현하여, 자식은 안보이지만 물려받아 갖다쓰고, 떠내려간 1개의 추상메서드만 구현하게 한다.**
        - **중 간추상층에서** 개별구현할 때는, **구현에 필요한 정보를 생추상클래스의 생성자로 받게 한 뒤, final로 불변화시켜, 자식은 못건들게 한다.**
- **2개 중 왜 발동조건 메서드를 중간층에 개별구현 미리 했을까?**
    - 액션을 미리 구현하면 안되나?
        - **액션과 나머지 2번째 적용 정책과 관련**이 있다.
        - 액션부터 구현하면 4 x 2 = 8개 다 만들어야한다.
    - **요구사항에서 `도메인과 1:1매칭을 이루는 기준 정책을 더 나중에 구상층에서 구현`하는 방향으로 설계**해본다???.
        - 조건을 만족해야 액션하므로, 조건부터 중간추상층에 구현한다?

###### SequenceDiscount(조건 정책) + AmountDiscount

1. Condition 정책 인터페이스의 2개 메서드 중 **중간 추상층에서 미리 구현할 메서드 1개를 선택한다.**

    - 발동조건 검증여부 역할(추상메서드)를 **개별구상체마다 막아놓을 것이다.**

      ![image-20220621235327034](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621235327034.png)

2. condition policy정책인 `DiscountPolicy`를 구현한 구상체를 **일반class구상화 대신 추상클래스로 구상화하여 중간 추상층을** 만들고, **발동조건 메서드만** 개별 구현하되, **개별구현시 필요한 인자를 추상클래스의 생성자로 받아 final필드에 박아준다.**

   ![cb5d5790-4193-4ebe-9429-7b9979d50a60](https://raw.githubusercontent.com/is3js/screenshots/main/cb5d5790-4193-4ebe-9429-7b9979d50a60.gif)
   ![image-20220622000731229](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622000731229.png)

    - return 문 빠진 스샷

3. **이제 중간추상층에 한번 더  나머지 `액션 메소드 1개만 정의`하는 구상층을 낼 수 있는데, 이 때 `DiscoutPolicy의 자식policy 중 1개인 AMOUNT(Discount)를 구현하고,  그 2번재 정책의 내용은 액션메서드를 개별구현 할 때, 로직에 적용`한다**

    1. 중간추상층 SequenceDisCount(추상클래스)를 상속한 구상체를 만든다.

        - **`추상클래스가 생성자를 새롭게 정의`했다면(for 중간에 미리 개별구현을 위한 인자 받아오기)**
            - **자식의 생성자도 `부모의 생성자 인자를 똑같이 받는 생성자를 최초 재정의`해줘야하며, 받은 인자를 부모의 생성자가 쓸 수 있게`super()`에 넣어준다. 이로 인해 `부모 생성자처리 -> 부모 필드 채우기` 가 `부모에서 이루어진체로 내려오도록 위임`해야한다.**
            - 이후에는 **자식의 생성자에, 자식구현을 위해 필요한 정보를 생성자 인자로 받아올 수 있다. super()의 부모생성자호출에 필요한 것만 먼저  필수로 받아줄 뿐이다.**

       ![0dc514c6-7460-44b4-9444-26efe721e4f5](https://raw.githubusercontent.com/is3js/screenshots/main/0dc514c6-7460-44b4-9444-26efe721e4f5.gif)

    2. **`condition policy`의 추상메서드 중 1개 `액션`역할 의 구현**이 아직 안되어서 넘겨받아 구현해야한다. **여기에 `2번째 정책(마커인터페이스지만)의 개념을 구현과 동시에 첫번째 정책 개별구현 메소드 내부에 개념을 녹인다.`해버리면 된다.**

        - AmountDiscount정책을 적용할 것이므로, 요금계산은 원래요금 - amount만큼 뺀 것을 반환해주면 된다.
            - 값객체는 값을 상태값으로 가지고 있으며 **스스로 연산책임을 가진다.** **인자로 똑같은 값객체가 주어진다면, ** 내부에서 편하게 계산되며, **연산값으로 new 새로운 값객체를 생성해서 반환한다.**
        - **class 내부 개별 구현로직에 필요한 정보들은, 생성자를 통해 받아오게 한다.**

       ![eed99ef4-0a4f-43d5-8b8b-9e696a4127e7](https://raw.githubusercontent.com/is3js/screenshots/main/eed99ef4-0a4f-43d5-8b8b-9e696a4127e7.gif)



###### SequenceDiscount(조건 정책) + PercentDiscount

3. SequenceDiscount(조건 정책)  + AMOUNT(2번째 정책)이외에 **나머지 정책조합을 구현**해보자. -> **SeqeunceDiscount(조건정책) + PercentDiscount**

    1. 조건정책을 **발동조건메서드만 개별구현하는 추상클래스로 구상화**하면서 조건메서드만 중간추상층에서 구현한다.

        - 이미 구현된 SeqeuenceDiscount

          ![image-20220622130711116](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622130711116.png)

    2. **만들어진 조건 정책  추상클래스를 상속**하면서 && **2번째 정책 마커인터페이스**를 구현한 최종 구상체에서 **액션메서드에 2번째 정책을 반영**한다.

        - Percent로 할인되는 2번재 정책을 반영할 것이다.
            - 원래 요금 fee에  .minus를 할 것인데
            - 원래 요금 fee에 percent(double형)만큼 .multi한 가격을 .minus해야한다.

       ![ad8da5aa-c9e5-4270-9242-1301b450751d](https://raw.githubusercontent.com/is3js/screenshots/main/ad8da5aa-c9e5-4270-9242-1301b450751d.gif)





4. **먼저 적용되는 조건 정책을 구상층의 이름에 먼저** 나타나게 수정

   ![image-20220622132257591](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622132257591.png)
   ![image-20220622132324480](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622132324480.png)



5. **실패 돌아보기**

    1. Main에 있는 정책이 적용되는 도메인 Moive입장에서는 특정Discount만 알도록 설계되어있다.

       ![image-20220622133329658](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622133329658.png)

        - movie입장에서는 **1:1매칭되어야하는 DiscountPolicy가 추상체로 묶였으면 좋겠다**

    2. 하지만, **DiscountCondition 정책을 먼저 적용하다보니**

        1. **발동조건1 Sequence or 발동조건2  ???**를 기준으로 묶이게 된다.
        2. **정책은 다시  Sequence 정책1, Sequnece 정책2 , 3, 4**로 분리되어버린다.
            1. **추상화가 잘못된 상태다.**

    3. **결과적으로 `도메인이 묶이길 원하는 인터페이스부터 중간추상층`으로 추상화하도록 2개의 인터페이스를 조합해야한다.**

        - 메서드 2개를 가진 인터페이스를 **중간추상층 화 해서 메서드 1개 막는 전략**을 썼더니,  **중간추상층화 한 인터페이스의 구상형 기준으로 나머지 인터페이스가 묶이게** 되며되.
        - **중간추상층의 구상형갯수2 x  같이구현할 구상형 갯수 4 = 8개의 모든 경우의수를 다 class형으로 만들어야한다.**



##### Policy -> Condition 순으로 정책 적용하기

- **2개 인터페이스 중 먼저 구현(중간추상층화)**한다 -> **그 인터페이스의 구상형들을 기준으로  최종정책들**이 묶인다.
    - **즉, 묶이길 원하는 인터페이스부터 먼저 구현(중간추상층)한다.**
- **`마커 정책의 구현`은 `조건 정책`의 `액션메서드`구현과 밀접하므로, 중간추상층에서는 액션메서드를 `먼저 구현`하여  `최종정책들은 1개의 발동조건 메서드만 구현`하게 한다.**

- **마커인터페이스와 조건정책인터페이스를 동시구현한다.**

    - 그래야 액션메서드를 개별구현하도록 내려온다.

  ​	![image-20220622144545612](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622144545612.png)

  ![image-20220622145356944](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622145356944.png)



###### AMOUNT + Sequence

1. 묶이길 원하는 **정책인** DiscountPolicy의 개별 **마커인터페이스부터** 구현하되, **액션메서드에 같이 구현**하기 위해, 정책조건 인터페이스도 **콤마로 같이 구현**한다
   ![f0f1be1e-56eb-4889-a8df-1fcb3f0a5e16](https://raw.githubusercontent.com/is3js/screenshots/main/f0f1be1e-56eb-4889-a8df-1fcb3f0a5e16.gif)

2. **아직 1개의 인터페이스만 구현한 중간추상층**으로서, **정책조건의 액션메서드에다가 정책(마커) 구상형의 실제 내용을 구현**한다.

    - Policy자체는 이제 추상화 된 것이다. AmountDiscount로 2번째 정책까지 구현한 최종 구상체를 묶을 수 있다.

    - **액션메서드 1개만 구현하기 위해 abstact class(중간추상층)화 한다**
        - 발동조건 메서드는, 이후 구현될 최종구상체의 Sequnece/Time Discount의 영역으로 남겨둔다.
    - class내부 **개별구현을 위해 필요한 정보들은 생성자에서 조달**한다.

   ![eb75a240-3e0f-4aba-8c26-cd9896023b59](https://raw.githubusercontent.com/is3js/screenshots/main/eb75a240-3e0f-4aba-8c26-cd9896023b59.gif)



3. 이제 조건정책의 발동조건 메서드 1개를 구현하는 최종 구상체를 만들어보자.

    - 2개의 발동조건(Sequence/Time) 중 Sequence를 구현해본다.

   ![159c5b32-9c13-48c1-8824-ba8a168ffc11](https://raw.githubusercontent.com/is3js/screenshots/main/159c5b32-9c13-48c1-8824-ba8a168ffc11.gif)



###### AMOUNT + Time

4. 나머지 정책조건 중 1개인 Time으로도 구현해본다.

   ![40a5116d-7d86-4318-8316-354791822eb1](https://raw.githubusercontent.com/is3js/screenshots/main/40a5116d-7d86-4318-8316-354791822eb1.gif)





###### PERCENT + Sequence

5. 마커 정책 PERCENT를 구현 & 액션메서드 구현를 위해 DiscountCondition을 구현한 중간추상층(추상클래스)를 만들고,

    - 다시 구상화하여 남은 발동조건메서드를 구현하며  SequnecePercenDiscount를 만든다.

   ![0fd0d766-9bf3-436d-9e28-7b3c9c981925](https://raw.githubusercontent.com/is3js/screenshots/main/0fd0d766-9bf3-436d-9e28-7b3c9c981925.gif)



###### PERCENT + Time

6. 마찬가지로 순서대로 구현

   ![8552b02f-c7e1-41a0-bbe1-c8e9c557ea2b](https://raw.githubusercontent.com/is3js/screenshots/main/8552b02f-c7e1-41a0-bbe1-c8e9c557ea2b.gif)





######  돌아보기

- 중간추상층은 **implments에 여러개 인터페이스를 구현** 할 수 있다.

    - implements `마커정책`, `조건정책 with 액션메서드+발동조건메서드`
    - **여러개 구현안됬으면, `중간추상층 1개 미리 메서드 구현`을 위해 무조건 조건 정책부터 구현했어야 한다.**

- 최종 구상층은 **extends에 1개의 부모만 상속**할 수 있다.

    - 중간추상층 1개만 필수로 상속할 수밖에 없다.
    - **만약, 구상층들간에 공통로직이 생겨도 `코드중복제거를 위한 추상클래스(템플릿메소드패턴) 추가`가 불가능하다**
    - 필수 추상클래스를 상속한 자식이라면, **`코드 중복`하려면 extends가 아닌 implments를 위해 전략패턴을 적용한 인터페이스로 해야한다**
        - 외부에 발생할 **별도의 전략객체들을 밖에서 받아와야한다**

- **왜 정책을 먼저 구현했냐?**

    - **도메인인 Movie에** condition이 아닌 **`policy구상형을 1:1매칭이 필수`였기 때문이다.**

        - **정책은 2가지가 반영되어야하니, 2번째 정책이 반영된 최종구상체들이 `policy를 추상층`으로 가져서 묶어야만 한다**

      ![image-20220622150349229](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622150349229.png)







##### Movie (제한된 정책 객체들만 받기 with 제네릭)

![image-20220203143256759](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203143256759.png)



1. Movie는 제약조건이 있다. **2종류의 할인정책(Amount, Percent) 중에 1가지와 1:1 매칭된 체로  생성되어야한다.**
   ![image-20220622151852444](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622151852444.png)
    - 하지만, 정책적용은 policy와 condition을 조합해서 class 생성하고, 그 객체를 받아들여서 사용한다.
    - condition 종류는 상관없이 policy기준으로 붙여야한다?
        1. policy를 정책조합의 중간추상층으로 조합하여, 정책들을 생성한다.
        2. movie는 특정형이 아니라 중간추상층 policy 2종류를 알아야한다.
            - **제네릭은 특정형을 알게 하지만, 최상위 추상층을 T의 제약으로 주, 특정추상층을 알게할 수 있다.**
                - 제네릭은 객체생성시 특정형을 알게하고, **class를 T로 정의**한다.
                - 제네릭을 익클-분신술구현시 특정형을 알게 구현한다면, 추상클래스를 T로 정의한다.
                    - 익클말고, 추상클래스의 구상class도 특정형을 알게 구현할 수 있을 것임.
                - **제네릭을 객체생성시 추상형을 알게 하고, class를 T로 정의한 뒤, `메서드(생성자) 파라미터도 T로 정의`해서 쓰면, `객체 생성시 메서드(생성자) 인자에 구상형들을 받아들일 수` 있다.**
            - movie마다 중간추상층(추상체)을 알게끔 만들면, 그 구상체들이 건네오게 알게 만들 수 있다.
                - 구상체들이 들어와도, 공통메서드로 처리되는 구상체들을 가지고 있다면,  if나 istanceof가 없이 바로 처리될 수 잇을 것이다.



2. **Movie를 요구사항에 따라 정리하며 만들어본다.**

    1. **movie 생성시, `정책의 최종구상형객체를  생성자 인자`로 받아들여야한다.**
       ![image-20220622154125462](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622154125462.png)

    2. **movie 정의시**, **`생성자 파라미터 정의부에는 여러구상체들이 올 수 있도록 추상체가`가 와야한다.**

        - **추상체를 어느레벨로 정해야할지가 문제**다

          ![image-20220622153803953](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622153803953.png)

            - 중간추상층 AmountDiscount를 주면, PercentDiscount 구상체들이 못온다.
            - 중간추상층은 **상위추상층으로  Policy와 Condtion 2개를 다 구현**했기 때문에 1개만 선택을 못하는 것도 문제다.

    3. **`제약걸린 제네릭 T`을 통해 `객체 생성시에만 특정형을 알고 쓰세요`할 수 있다.**

        - **`제약조건upperbound를 상위 추상층`으로 주면, 어떤 형이든 아는 객체가 생성될 것이다.**

            - 2개 인터페이스를 2개의 상위추상층으로 보는 방법은 `&`를 써서 연결하면 된다.

          ![image-20220622154457138](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622154457138.png)

    4. **상위추상층을 upperbound로 하면 -> 객체 생성시 `특정 중간추상형을 알게 만들 수 있고` -> 구상층의 추상체로서  `생성자 인자에는 특정 중간추상형의 최종구상체들`을 넣을 수 있게 된다.**

        - **제네릭의 사용 = 특정형을 안다 = `파라미터나 변수, returntype의 T자리에 특정형으로 정의된 상태`의 객체가 생성된다.**

       ![image-20220622155258816](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622155258816.png)

        - **제네릭의에 넣은 `특정 중간추상형(파라미터,변수,returntype)`과 `사용되는 인자`가 매칭이 안되면 에러가 날 것이다.**

          ![image-20220622155514296](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622155514296.png)

    5. **이제 외부에서는 제네릭에 `요구사항에 따른  특정 추상형을 아는 Movie`객체들을 만든다.**

        - 영상으로 보기

       ![c78f265d-f4b9-4c26-aa8b-565679c86827](https://raw.githubusercontent.com/is3js/screenshots/main/c78f265d-f4b9-4c26-aa8b-565679c86827.gif)

    6. 응용 생각해보기

        - **제네릭의 upperbound는 `if 분기를 처리하는 각 전략객체(구상체)들의 추상체(들)`가 대상이다.**
        - **if분기를 구성하는 조건이 2개다? -> 인터페이스 2개로 추출 -> 2개 인터페이스를  implements하는 추상클래스(중간추상층) 1개 생성** -> **제네릭으로 변경하여 특정중간추상층을 사용시, 상위추상층으로 uppperbound에 `A & B`**
            - 각 if를 구성하는 조건이 3개다? -> 인페 3개 추출(개별 전략객체생성) -> 3개를 imp하는 추상클래스(중간추상층) 1개 생성 -> 제네릭으로 변경사용시 upperbound에 상위추상층으로 `A & B & C`를 놓고 사용
        - if는 추상체 -> if 갯수만큼 구상체class  정의후, 외부에서 객체 처리기를 만들어서 주입
            - 그 객체class -> 객체 생성을 `형`으로 대신해주는 것이 `제네릭`







##### Movie 2(구성과 정책 적용)

1. **Movie가 제네릭을 쓰고 있는 이유는?**

    - 정책 구상체 객체를 받아가서 사용하면 안되나?
    - **정책 구상체가 2개 인터페이스 조합**인데, 요구사항에 **`movie마다 1개 인터페이스(policy)에 대한 1종류씩만 적용`하라는 제약이 있어서 `그 하위 구상체만하라는 제약`이 걸림.**
        1. 2개 인터페이스를 조합하되, 1종류 제약이 걸린 인터페이스를 **종류마다 중간추상층으로 구상화**한다.
        2. 제네릭이 upperbound는 2개인터페이스를 다 걸어놓되, **movie생성시, 특정 중간추상층의 하위 구상체들만 알도록 -> 인자로 받도록 제약을 준다.**

    - **제네릭을 사용할 때, (중간)추상체를 넣어서 추상체를 알도록 할 수도 있다!**

2. 제한된 1개종류 policy의 정책들도 **여러개가 들어올 수 있다고 가정하고, 1개 인자 -> `...`가변배열 인**자로 받아준다.
   ![b7b1a93f-b575-4b4f-80a4-0428078ed7bf](https://raw.githubusercontent.com/is3js/screenshots/main/b7b1a93f-b575-4b4f-80a4-0428078ed7bf.gif)

3. 여러개가 들어와도, **중복허용 없이 종류별 1개씩만 있으면 된다면, `생성자 내부에서 빈 hashSet필드에  배열-> list -> addAll기능`으로 `빈 Set<T> = new HashSet<>(); 필드`에 넣어준다.**
    - **영화별로, `적용된 할인정책을 1개씩만 소유하고 있으면, 꺼내서 돌면서 적용만` 하면 된다.**
    - **list는 index가 관여한다 -> 결국엔 값으로 식별된다.**
        - **객체지향에서는 list에서 필수인 index인 값context보다  `객체context = 식별자로 식별`해야한다**
    - **set은 index없이 `들어간 객체`로만 식별할 수 있다.**



4. movie는 정책을 적용할 수 있는 `할인정책 객체`와  `정책적용 대상`인 movie 1개의 요금 `fee`를 가지고 있기 때문에, **정보전문가패턴**에 의해서 정책을 적용할 수 있다.

    - my) **정책적용대상(fee)를 가진 곳에 정책객체를 생성시 넣어주는 것 같다.**

    - 정책의 액션메서드인 calculateFee가 똑같이 Movie에서도 동일한 네임으로 적용해보자.

    - **가지고 있는 할인정책은 종류별로 여러개이지만, `외부에서 받아온 해당영화의 상영정보(screening)`에 의해, 맞는 것 1개만 isSatisfiedBy 된다고 가정하고, ealry return시킨다.**

        - 순서를 고려하지 않는 set이기 때문에, 돌면서 해당하는 정책 객체는 1개라고 가정한다.

    - **돈계산시, 액션메서드가 Money값객체를 반환하므로, 메서드체이닝이 가능해진다.**

        - 할인된 요금에다가 `audienceCount`만큼 곱한 **해당 영화 상영 전체 금액**을 구한다?
            - movie는 할인된 1개 영화가격이 아니라, **할인된 가격 x 전체 인원수의 총 매출을 계산하는 것으로 가정한다**

      ![b5a358ee-0463-4e4f-9c55-27e2a08c1774](https://raw.githubusercontent.com/is3js/screenshots/main/b5a358ee-0463-4e4f-9c55-27e2a08c1774.gif)

        - **그럼, 할인요금 적용 안될 때도, 인원수만큼 곱한 금액을 반환한다.**

          ![83bd9005-c9dc-44cc-9cc8-20ff947f5be1](https://raw.githubusercontent.com/is3js/screenshots/main/83bd9005-c9dc-44cc-9cc8-20ff947f5be1.gif)







##### Money(final 필드 1개의 데이터 객체 - 1개의 final 필드가 연산기능 제공으로 변할시 -> 새 객체로 반환하는 불변의 값객체)

![image-20220203154025289](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203154025289.png)

- 값에 class라는 껍데기를 씌우고, 상태값으로 값을 가진다.
- 상태값인 값이 자신의 메서드에 의해 연산되며, **return시 껍데기를 씌운 `새로운 객체로 를 생성해서 반환`**한다.
    - 연산 전 상태값+껍데기는 불변성이 유지된다.
        - **모든 필드는 final이다.**
        - 값객체에서 **상태값의 값 필드는 private으로 외부노출 안된다.**
    - 자신의 기능의 결과가 자기자신type이라면, **자신의 메서드들을 체이닝할 수 있다.**
- 재화 등 0이상이어야하는 상태값을 가진다면 **return 하한선을 확인한다.**

- 자신에 대한 **연산 기능(메서드)는 인자로 똑같은 값객체를 주는 것이 가장 좋**으나 값이 들어오는 경우도 있는데

    - 이는 **다른 데서 객체 대신 값을 써서 스노우볼이 굴러서 오는 것**

    - 어떤 객체context라도, 1개의 원시형(값)을 썼다면, 전역적으로 값context를 만든다.

        - 되도록이면 인자든 뭐든 싹다 객체를 써야한다.

      ![image-20220622201057708](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622201057708.png)
      ![image-20220622201123945](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622201123945.png)

    - **값들을 객체로 썼다면? 어느 순간 `abstract numbers`라는 추상화가 가능할지도 모른다.**

        - **숫자도 객체로 만들어야 추상화될 가능성**이 있다.
        - 객체로 만들어야 -> 구상체들의 2개이상의 책임 -> 추상체의 1개의 역할 -> 한번에 처리된다.
        - primitive가 온 순간 -> **확장 = 추상화**는 없어진다. 반드시 객체로 메세지를 주고 받아야한다

- 연산 뿐만 아니라 **비교까지 모두 값객체의 메서드로 하자.**

  ![image-20220622201430837](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622201430837.png)



- 장점 정리
    1. 번거로워도 값을 private + final 필드로 **데이터 은닉**화 -> **외부에서는 값을 제공된 기능으로만 변경할 수** 있다.
    2. 스스로 연산후 **새 객체로 return -> 기존의 상태값에 간섭못함 -> `동시성 문제가 없다`**
        - **확정되어있는 도메인이나 연산 -> 값객체로 바꿔서 동시성 문제를 없애자.**



- **값 객체를 필드로 가진 객체들은**

    - 그 필드가 자기 기능으로 변화하면, 새 객체를 반환하므로 **값객체 필드는 재할당 할 수 밖에 없어서 final이 불가능하다.**

        - 아래는Theater가 가진 Money amount 필드가 재할당 되는 것

          ![image-20220203202450918](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203202450918.png)

    - **`재할당되어 변하는 (no final)필드`(값객체 필드 포함)는 반드시 private으로 외부노출을 막아서, 동시성 문제를 없앤다.**

        - 포인터의 포인터 중, 포인터 자체가 바껴서.. 못찾아갈 수 있다.
        - **재할당되는 필드는 외부에서 아예 사용 못하게 막**아야한다.



##### Reservation(여러 final 필드의 순수 데이터객체 - 연산없는 값 객체)

![image-20220203162318753](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203162318753.png)

- Ticket을 대신하는 재료객체이다.
    - data-oriented class라고 볼 수 있다.
    - **정보의 묶음인 `재료객체, 데이터객체`는 `null 대신 public static final `로 상수로 선언한 `NULL객체`를 쓰자.** -> **데이터객체는 `생성/조회 전 검증 실패시`에 null대신 NULL객체를 반환해준다.**
        - **값객체 class내부에 선언**한다.
        - **public static** - 돌려쓴다.
        - **final** - 불변이다. 돌려쓰는 + 불변 -> 상수
        - **데이터객체는 반환되는 경우가 많아서, 검증 실패시 등에서 NULL객체를 만들어주는데** EMPTY or NONE이라는 변수명을 사용한다.
            - **`데이터객체Class.EMPTY` 혹은 `데이터객체Class.NONE`으로  static한 상수로 사용됨**
- **재료객체, 데이터객체는 모두 `순수한 데이터정보`로서  `final`필드를 사용한다.**
    1. 극장
    2. 영화
    3. 상영정보
    4. 예매한 인원수(count)



- 데이터 객체도 **값객체**다.

    - **`연산을 제공안하는` 값객체 = `재료객체, 데이터객체` with final**

    - **값객체는 NULL객체를 고려한다.**



- **`전체가 final필드`를 가진다면, 스레드 안전이다.**



##### Screening(final필드 + 외부조건에 따라 변하는 필드의 데이터객체 - 변하는 필드만 final떼고 trigger+action기능을 제공하여 새객체 반환없는 값객체)

![image-20220203164740902](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203164740902.png)

- **대부분이 final필드로 박아지면, 데이터객체로서 값객체라 부를 수 있다.**

    - final필드라도, **외부 할인정책의 개별구현시 에서 인자로 받아 내부 sequence와 whenScreened를 getter없이 조회할 수 있게 `public final`이다.**

      ![image-20220622204229616](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622204229616.png)

- 여기서 `seat` 필드는

    - **final이 아니다 -> 나중에 변한다.** by setter(받기기능) or 연산기능 등
    - **private이다. ->  변하더라도 `외부에서 제공된 기능을 통해서만 변한`다.**
        - 예매하는 사람이 예매를 했을 경우, **나 자신을 검증하고, 그만큼 가용좌석수가 까여야하기 때문**이다.
        - 예매한 좌석수: count(외부에서 들어옴)
        - 가용 좌석수: seat(검증되면, count만큼 까야됨)
        - **외부의 인자에 의해 검증시 사용되고(trigger)되고, 차감(action)되는 필드다.**
            - trigger : count이상 seat를 현재 가지고 있니?
            - action: trigger를 이용해 만족할경우, count만큼 좌석수를 seat에서 빼라.

- **seat가 변하는 것은 `외부(count-예매좌석수)`에 의해 변하는(`예매권 발행시 차감되어야하는`) 필드다.**

    - **seat의 변화(차감)는 `외부에서 예매를 발행(in theater)할 때` 작동하며, 현재 인스턴스에 반영되어야한다(차후 setter나 다른 기능으로) -> final이면 안된다.**

        - **trigger만족시, 예매권 발행전, 자리수 차감**

    - **`외부 인자`라는 `조건에 따라 변하는 필드`는, 그 내부에서 1) trigger(변화조건) + 2) action(변화) 메서드를 가져서 `외부에 제공(외부에서 예매발행시 작동할 것임)`한다.**

        1. `hasSeat(int count)` : **외부조건에 따라 변화조건 검증 기능**
        2. `reserveSeat(int count)` : **외부조건에 따라 변화** + **내부에서 trigger사용함**
            - 액션메서드는 항상 **`내부에서 if trigger메서드를 사용해 검증후 액션로직을 구현`한다.**
                - 내부에서 사용하지 못하는 경우(trigger가 나중에 작성될 경우)는 **외부에서** trigger -> action순으로 사용하면 된다.
                    - ex> 정책객체는 적용객체 내부로 들어가서 외부에서 사용됨.
            - **`trigger 조건을 만족하지 못할경우, thr RuntimeException`을 내면 된다.**

      ![2c21447a-2b16-441e-ae45-d16b9fdcf5ea](https://raw.githubusercontent.com/is3js/screenshots/main/2c21447a-2b16-441e-ae45-d16b9fdcf5ea.gif)





##### Theater

![image-20220203165805132](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203165805132.png)

![image-20220203170438126](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203170438126.png)

- 일단 자본금이 값객체Money로 대체되었다.

- theater의 저장정보 추가

    - **모든 정보들이 theater`생성후 저장`되며 `여러개(Collection)으로 저장될 예정이면 빈컬렉션을 default값으로 초기화해서 저장`한다**
    - 기존에는 ticketOffices들만 저장
        - ticket을 받기기능으로 가져가던 ticketOffice들 목록저장을 `List`에서 하다가 **객체지향으로서 index = 값이 없는 `Set`으로 저장한다.**
    - 상영할 movie와 movie에 딸린 상영정보들을 Map으로 저장
        - movie만 저장한다면 set으로 했을 것인데
        - movie별 screening도 저장해야하므로 **상위1 당 하위도메인들N을 Map에 한번에 저장한다.**
            - 이 때, **하위도메인**상영정보**들**을 map의 **value자리에** List가 아닌 **Set에 중복을 허용없이 여러개를 저장**한다.
        - `Set<Screening>`은 map의 value값인데 없을 수도 있다. **value가 없을 수도 있는 key값을, `key값만 저장가능`하게 하기 위해 `value의 default NULL값`도 값객체처럼 static final로 만들어놓는다.**

  ![image-20220622213906070](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622213906070.png)

- 상위도메인movie를 key로 받기기능

    - **받기기능에 중복 확인시 false로 알려주기 위해 return이 boolean형이다.**
    - key등록시 value인 `Set<Screening>`은 빈 set로 넣어서 초기화한다.

  ![image-20220622214601165](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622214601165.png)

- 하위도메인screening을 value로 받기기능

    - **하위도메인의 CRUD는 항상 상위도메인과 같이 인자로 받는다.**
    - 상위도메인이 key로 등록안됬다면, false로 반환한다
    - 등록된 상위도메인key가 존재하는 상황이라면, **이미 default로 `빈 set을 value로 생성된 상태` 만들어놨기 때문에 `map.get(상위도메인)`으로 `꺼낸 상태에서 add만` 해주면 된다.**
        - put이 아니다. get한 뒤, 빈 셋에 add다.

  ![image-20220622214952589](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622214952589.png)



- client가 정해준 rate를 가지고,  ticketOffice와 계약 후, 성공시 저장목록에 추가

    - step1에서 기존에는, 일방적으로 theater만  -> ticketoffice을 알고 목록을 list에 저장만 했었다.
        - 내부생성되는 티켓도, 와서 받기기능으로 받아가기만 했다.
    - **지금은 쌍방 계약이라 서로 알아야한다.** 계약조건에 해당하는 외부인자 rate도, 계약할 ticketOffice와 같이 받는다.
        - ticketOffice도, this로 현재 theater 인스턴스를, **커미션율을 같이** 들고가서, 계약을 저장한다.
            - 차후 나오지만, 여러극장과 계약할 수 있기 때문에, theater별 rate를 따로 필드로 저장한다.
            - ticketOffice가 이미 계약한 theater일 경우 계약메서드 returndmf false를 반환해줘서 계약이 무산된다.
            - 계약 성공시 계약ticketoffices의 set에 추가해주고, 성공여부를 client에 알려준다.
                - **성공/실패가 있는 트랜잭션 메서드의 경우, boolean형으로 작성하자!**

  ![image-20220622220958815](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622220958815.png)



- 계약을 취소할 수 있다.

    - 이것도 ticketOffice가 허락을 t/f로 알려줘야 진행된다.
    - **my) 계약도 거래처럼, 계약을 해줄지말지 결정하는 사람(ticketOffice)가 갑이고, 을에서 인자로 받아서, 내부에선 this와 함께 갑의 기능에 계약 가능한지 물어본다.**
    - 저장목록에서 삭제와 마찬가지이므로, 존재여부부터 검사한다. + 갑이 계약취소를 해줘야한다.
        - 취소가능할 시, set에서 remove로 객체를 제거한다.

  ![image-20220622221703873](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622221703873.png)



- **딸린 하위도메인(Screening) 전체 조회**

    - 상위도메인 객체(fk대용) 로 전체 조회한다.
    - 상위도메인 존재여부 -> 해당도메인 존재여부를 확인부터 한다.
        - 없으면 빈값을 보내야하며, 자주 반환되니 매번 생성하지말고, **컬렉션의 NULL객체로서 EMTPY의** 상수로 만들어놓는다.

  ![image-20220622225228869](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622225228869.png)





- **유효한 screening인지 -> 상위도메인 존재 및 상-하위도메인 연결확인**

    - **아래, 예매권 검증에서 사용될 예정이다.**
    - 예매권의 영화, 상영정보가 제대로 된 정보인지.

  ![image-20220622225925643](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622225925643.png)





- **예매권 발행주체로서, 고객이 산 예매권을 검증하는 책임도 가진다.**

    - 예매권의 정보가, 발행한 theater가 맞는지부터 시작해서 내 정보들로 비교해서 반환한다.
    - 예약좌석수count는 theater가 아니라 client에서 지정해주기 때문에, 외부에서 사용했던 것을 그대로 받아온다.
    - 내부에 존재하는 상-하위도메인 정보는 위에서 정의한 메서드로 검증한다.

  ![image-20220622233040464](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220622233040464.png)





- **Customer와 거래는 TicketSeller가 하는 데, 예매(reserve)의 책임은 Seller -> Office -> Theater까지 요청이 들어온다.**

  ![image-20220623002927648](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623002927648.png)

    - 사는 놈 Customer가 스스로 예매하지만, 갑인 Seller의 예매(**거래**)기능 이용

      ![image-20220623003127672](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623003127672.png)

    - TicketSeller-> TicketOffice로 예매권 **생성** 요청
      ![image-20220623003209741](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623003209741.png)

    - TicketOffice는 -> Theater에게 예매 **생성** 요청

      ![image-20220623003551966](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623003551966.png)

    - Theater는 **발행정보를 통한 검증을 거쳐** 예매 **생성**하여 office -> seller -> customer에게 반환함

      ![image-20220623003852718](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623003852718.png)

- **예매발행 검증(trigger포함) 통과 <-> 예매권 발행 사이에서  `특정 이용가능좌석수가 차감 action`이 Screening 객체에 필드에 적용되어야한다.**

    - screening 객체 속에 이용가능자석수가 field로 담겨있으며, 거기에 trigger와 action메서드를 제공하니, 이용해서 차감시켜야한다.
    - 외부인자에 의해 액션이 들어가는 객체screening은 trigger와 action을 제공한다.
        - **trigger는 `if (! trigger성공) -> early return`형태로 사용하고, 이 조건문 아래에서는 trigger성공 상황으로서 action한다.**

  ![image-20220623004852900](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623004852900.png)



- 데이터객체의 발행은, 새객체를 생성해서 반환해준다.

  ![image-20220623005132425](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623005132425.png)







##### TicketOffice

![image-20220203205814111](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203205814111.png)

![image-20220203210611290](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203210611290.png)

- 자본금을 값객체로 가지고 태어난다.

- **Theater와 계약을 하며, 계약취소 기능도 있다.**

    - **트랜잭션 관계라서 서로 안다.**

        - 여기선 office를 거래의 갑으로 보고 **먼저 처리하고, 성공여부를 을(theater)에게 boolean 메서드로 t/f여부를 전달해줘야한다.**
            - 을(theater)는 갑의 성공여부를 확인한 뒤, 계약/계약취소를 한다.

    - 계약은, 계약조건(rate)와 함께, theater를 빈 값으로 초기화된 map field에 add(put)하는 것이다.

        - 1theater는 여러office와 거래하여, office목록을 set에 저장하지만,
          ![image-20220623121224734](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623121224734.png)
        - 1office도 여러theater와 거래 가능하여, **theater목록 + 거래 theater에 딸린 거래조건rate을 map**으로 저장한다.

    - 거래란, **서로의 컬렉션 field에 거래대상을 저장해놓는 것**이며, **거래조건을 제시하는 office가 갑으로서, 을theater에서는 office목록만 vs 갑office는 theater목록을 + 거래조건rate과 함께 저장한다.**

      ![image-20220623122149211](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623122149211.png)
      ![image-20220623122317916](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623122317916.png)

    - 을(theater)는 **갑의 계약/계약취소 성공시에만 진행된다. `if  !성공`으로 실패시, 을도 실패를 반환한다.**

      ![image-20220623122420396](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623122420396.png)



- 기존에는 **Theater에게 데이터객체(Ticket)을 받기기능(빈 컬렉션필드에 add)으로 가져와 창고처럼 list필드에 모으고** 있다가 **Seller가 소속기관으로 생각하고 필드로 받아와(setter)  -> seller 내부상황에 office에서 office의 주기기능(getter)을 이용해 받아왔지만 `창고 역할은 사라졌다`**

    - 기존

      ![image-20220623120245528](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623120245528.png)



    ![image-20220623120613128](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623120613128.png)

- **지금은, 발행주체인 Theater에게 받아와 -> Seller에게 반환해주는 기능을 제공해, `검증 후 생성된 것을 받아 전달`만 역할만 한다.**

    - 창고역할은 사라짐.

  ![image-20220623120807813](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623120807813.png)



- office는 창고역할은 사라지고, **seller가 예매발행 요청**을 **theater가 아닌 연결된 office에게 먼저 발행 요청**한다.
  ![image-20220203210611290](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203210611290.png)

    - **내부에서는 궁극적으로 실제 발행을 해주는 theater에게 발행요청을 또 보낸다.**

    - 하지만, **seller(판매자)에게 발행 요청을 받고나서, theater(생산자)에게 발행 요청하기 전에 office가 하는 일이 있다.**

        - **왜냐면, `customer가 사는 예매권`에 포함된 정보들이, 거래하는 seller의 소속기관인 office와 `계약된 theater가 발행가능한지`부터 시작해서, `사려고 선택한 정보들이 유효한지`를 확인해줘야한다**
        - 즉, office-seller의 **생산자(theater)로부터 판매자(seller)까지 `중개자 역할`을 하므로, `중개자와 계약된 생산자가 발행가능한, 데이터객체를 요청하는지`를 검증해줘야한다.**

    - 요약하면 **seller(판매자)가 생성요청하는 데이터객체(Reservation)의 정보**가

        1. 나와 계약된 생산자로 요청하는지 by theater 인자
        2. 중개자와 계약된 생산자가 만들 수 있는 것인지(유효한 정보인지) by movie, screening 인자
        3. 생산 가능한지(이용자석수 남아있는지) by screening 인자
            - **중개자로서 검증후에, 생산자에게 생산 요청한다.**

      ![image-20220623125516698](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623125516698.png)

    - **또한, 중개자로서, `생산자theater가 올바르게  데이터객체(예매권)을 생성`했으면, `seller에게 넘겨주기 전에, 생산자와 계약한 거래조건으로 나의 commission을 챙기고, 나머지를 생산자에게 준다.`**

        - 중개자는 **판매금 계산 by 정책적용가능한 movie객체**

        - 중개자는 **거래조건대로 커미션 계산**
        - **커미션은 중개자의 자본금에 plus**
        - **판매금 - 커미션을, 생산자의 자본금에 plus**
            - 후에 seller에게 예매권을 넘겨준다.

      ![image-20220623125701706](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623125701706.png)





##### TicketSeller

![image-20220203211346707](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203211346707.png)

- Customer와의 **거래에 대한 갑**으로서
    - **판매할 물건을 NULL객체로 만들어놓고(변수재활용)**
    - **물건 가격을 계산한다**
        - **movie**가 정보전문가패턴으로서, 정책도 가지면서 태어나, **계산의 책임이 있었다.**
    - 상황이 **사는놈(Customer)가 돈을 가지고 있는지 검증**하여
        - **사는놈이 돈이 있으면, 중개자 office에게 예매권을 받아온다.**
            - 외부에서 받아온 객체는 **NULL객체인지 검사를 꼭 한다.**
                - **받아온 물건이 제대로 되었다면, 사는 놈(Customer)에게 돈을 차감한다.**
        - **if 돈 검증 -> if 물건 확인**의 과정에서 **애초에 돈부터 없으면**, NULL객체 그대로 반환하게 한다.
            - **early return이 아니라, 상황마다 return해야할 객체 값이 재할당 될 경우, 미리 제일 쉬운 상황인 NULL객체로 만들어놓는 것**

- **기존** Ticket을 팔 때도 마찬가지 로직이다.

    - **seller는 돈계산을 하지않고, 소속기관이자 중개자인 ticketoffice가 커미션 + 생산자에게 커미션 뺀 금액을 챙긴다.**

  ![image-20220623133845692](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623133845692.png)

- 현재는 Ticket대신 Reservation으로 바꼈다.
  ![image-20220623142554754](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623142554754.png)

    1. **거래는 `중첩if를 만족할때만 성공(특수)` & `(그외 대부분의 코드분기) 중첩된 if의 else마다 매번 실패값을 return`**해야하므로 **`애초에 실패값인 null객체를 반환 변수에 할당`해놓고 -> `<중첩if성공시에만 바뀌는 else없는 + earlyReturn없는 코드>를 짠다.`**한다.

        - **여러 조건을 통과(중첩if)해야 성공이고, 그외 실패(NULL객체반환)일 경우**

            - 중첩if의 else마다 발생할 **실패값를 미리 변수에 담아두고**
            - 중첩if의 성공만 **변수에 성공값 재할당**
                - **else는 코드를 안짜고 무시하면 ->  그대로 default 실패값**
            - 마지막에 **변수만 반환**하면
                - **else 실패 코드의 중복없이 성공만 고려하는 코드가 된다.**

        - 만약, 중첩if의 else마다 실패코드를 작성한다면?

          ![image-20220623141344853](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623141344853.png)

            - **return 실패값이 if의 else마다 반복**된다.

        - 만약, 중첩if시 성공을 드모르간 법칙을 적용한 ealry return으로 짠다면?

          ![image-20220623141500910](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623141500910.png)

            - **not A시, not B시 `실패값을 return`하는 것이 `중복` 것은 마찬가지다.**

    2. 물건의 가격을 받아오고

    3. if 사는 놈이 그만큼 돈이 있다면

        1. 그때서야 외부에서 물건(Ticket)을 받아온다.

            - **돈 있는 것을 확인한 뒤에야, 중개자 office가 돈 계산하며 theater에게 만들라 시킨다.**

            - 바깥의  Ticket 변수에 채운다.

        2. if 물건이 제대로 되었으면(not null객체)

            - **외부 물건을 가져왔는데, 내부 사정에 의해(이용좌석수 제한 등) 생산이 안될 수 도 있다. -> 생산 및 내부 돈계산이 안됬을 것이니, 사는 놈의 돈 차감(피해) 전에 처리된다.**

            1. 사는놈의 돈을 차감한다
                - 가져온 물건이 제대로 되었을 때만, 돈을 차감함.

    4. 3에서 if 2개(돈있냐/물건제대로됬냐)를 다 통과했다면, not null ticket   vs  if 통과 못했다면 default null티켓을 받는다.



- my) else없는 중첩if문 -> **미리 else에 해당하는 코드가 위에 세팅되어있어야한다.**

- **`물건의 값`은 ticketOffice(중개자)가 물건 떼올 때 미리 커미션 -> 생산자(theater)는 커미션 뺀 금액을 함께 챙기고, `seller는 고객의 돈을 차감`만 시킨다.**

  ![image-20220623143046485](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623143046485.png)



##### Customer

![image-20220203212017919](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220203212017919.png)

- 자본금을 값객체로 가지고 태어난다.

- 사서 받아올 물건을 저장할텐데, **태어날 때부터 없는 물건으로서, 거래후 void setter처럼 받아오니, 필드를 null대신 NULL객체로 초기화 해놓는다.**

  ![image-20220623163348741](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623163348741.png)

- 사는놈은 을로서, 갑인 파는놈에게 거래를 요청하길, **스스로 기능을 호출하되, `인자로 갑(파는놈)` + `사고 싶은 물건의 정보`을 데리고 와서 갑의 기능을 이용하여 `this`로 자신을 보낸다.**

    - **my)을인데, 자기가 안가고 스스로 기능 호출 해서`갑을 데리고 오는 이유`?**
        - **갑의 거래기능을 이용해 데이터객체를 받아서 저장해야하므로 시작을 먼저 자기가 스스로  void(setter)로 호출한다.**
    - seller, office, theater의 .reserve()와는 다르게, 누구에게 데이터객체를 반환해주는게 아니라, **받아서 자기가 가져야하니(필드에 박아야하니) void로 reserve()를 정의**한다.

  ![image-20220623163902430](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623163902430.png)



- 상태값으로 not final 값객체를 가졌다면, **외부에서 값객체를 변화시킬 api를 제공한다.**

    - 을로서, 파는놈에게 돈 가졌는지 검증 당한다.

        - **값객체끼리의 비교는 내부 기능으로 정의해서 처리한다**

      ![image-20220623164113484](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623164113484.png)

      ![image-20220623164235096](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623164235096.png)

    - 을로서, 돈 차감을 파는 놈에게 맡긴다.

      ![image-20220623164157721](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623164157721.png)





##### Main 다시보기

- Movie가 중간추상층을 특정형으로 알도로 제네릭을 사용

    - 그 특정형이 T대신 생성자/메서드 인자, 변수형으로 사용되었을 것이니 -> 추상층의 구상층들을 생성자의 인자로 주입 가능해졌다.

      ![image-20220623164753954](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623164753954.png)

    - AmoutDiscount냐 vs TimeDiscount냐의 if분기를 제네릭으로 제거한 것으로 볼 수 있다.

        - **2개의 정책이 적용된 최종 정책객체를 DI주입만 해주면 되는 것이다.**
            - 현재 사용된 정책: sequence가 1일 때, 1000원을 깍아주는 SequenceAmount정책

- Theater에 movie를 add한다.

  ![image-20220623165215363](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623165215363.png)

    - setter는 1개의 데이터를 필드에 바로 박도록 받기기능
    - add는 1개or여러개의 데이터를 **`빈 컬렉션`으로 초화된 필드**에 추가하도록 받기기능

- Theater에 screening을 add한다.

  ![image-20220623165249915](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623165249915.png)

    - screening은 movie에 딸린 하위도메인들이다.
        - fk로서 movie객체도 같이 넣어준다.
    - **상-하위도메인들을 연결하면서 받기기능은 내부 `빈 Map필드`에 put하는 것이다.**

- Theater는 ticketoffice와 계약한다.
  ![image-20220623165512630](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623165512630.png)

    - theater내부에 저장해야하므로 인자로 받아서 계약한다.
        - office도 계약한 theater을 내부 저장하기 위해, this로 받는 과정이 내부에 있다.

- TicketSeller는 ticketoffice를 소속기능으로서 받아서 내부에 저장한다.
  ![image-20220623165602104](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623165602104.png)

    - 계약관계가 아니라서 일방적으로 알아서 박기만 한다.?
    - seller 내부에 저장해야해서 seller가 office를 인자로 받아온다.

- customer가 seller에게 거래를 요청한다.

    - theater가 상영하는 것들 여러 상영정보중 첫번재 것을 선택했다고 가정하기 위해 for + break;
    - 을이지만, **seller에게 최종적으로 받은 데이터객체를 받아 저장해야하기 때문에 받기기능을 이용하기 위해, seller를 인자로 알고 데려온다**.

  ![image-20220623165759368](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623165759368.png)

- theater는 발행권자로서 정보가 많으므로, customer가 가진 데이터객체(예매권)을 검증해준다.
