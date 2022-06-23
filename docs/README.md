### Description
- object book의 Theater 예제의 상호의존성을 수정한 설계도대로 객체class를 하나씩 만들어간다.
- `step1` : Client코드부터 짜야하지만, 상호관계를 파악하기 위해 class들을 순서대로 설계한다.

### STEP1(기본 설계)
- theater 수정 설계도대로 class 설계하기
    - 객체 상호의존표
        ![image-20220122214556326](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220122214556326.png)
    - Diagram
        ![image-20220621120453611](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621120453611.png)


### STEP2(티켓->예매권으로 변경 + 2개의 인터페이스로 할인정책 반영)
- theater를 예매권 구입으로 변경(step1과 별개 코드)
- 할인 정책을 반영
    - **`정책조건`(메소드2(trigger+acton) 인터페이스)와`정책시행`(마커 인터페이스)**를 따로 두고
    - 2 인터페이스를 구현 + 1개의 메서드(action)메서드 개별구현한 **중간추상층의 추상클래스** 구현한 뒤
    - 중간추상층별로 구상층에 나머지 1개 메서드(trigger)를 구현하는 구조 사용
- 객체 상호의존표
    ![image-20191124163632027](https://raw.githubusercontent.com/is3js/screenshots/main/68747470733a2f2f747661312e73696e61696d672e636e2f6c617267652f30303679386d4e366779316739393633666b337a766a333131713069676d79372e6a7067)
- Diagram
    ![image-20220623170546098](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623170546098.png)
    ![image-20220623170509341](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623170509341.png)

### STEP3(정책조건은 전략패턴 + 정책시행은 템플릿메소드패턴으로 할인정책 반영)
- step2에서 SOLID원칙을 반영하도록 변경함.
- 정책은 2개 인터페이스 -> policy는 템플릿메소드패턴, condition은 전략패턴을 사용하도록 변경함
  ![image-20220624005906639](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220624005906639.png)
#### 과정

##### Movie

![image-20220205161147187](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220205161147187.png)

- T형(Amount or PercentDiscount) 할인정책 객체 여러개를 받던 것을 1개만 받도록 설계됨.

- **정책조건 만족시, 총 요금을 `movie내부에서 fee`를 이용해 movie내부에서 계산 -> `정책 객체에게 movie의 fee를 넘겨서` 알아서 정책객체가 알아서 계산하도록 `계산 위임`**하도록 바뀜.

  ![image-20220623223839368](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623223839368.png)





##### DiscountPolicy

![image-20220205161328082](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220205161328082.png)

- 기존에는 인터페이스내부에 policy종류별 마커인터페이스들 담고 있었으나, **caculateFee()에 대한 `템플릿메소드 패턴`이 적용된 `추상클래스`로 바꾸었다.**

    - 물려지는 public은 다 템플릿메소드라고 부른다.
        - addCondittion, copyCondition, calculateFee
    - 자식(구상클래스)에 위임된 protected abstract 메서드를 훅메서드라고 부른다.
        - calculateFee
    - **템플릿메소드패턴은 언제???**
        - 구상형의 수가 한정된 상태
        - **구현층에 공통 로직이나 공통 필드가 존재할 때, 중복제거**용으로
    - **전략패턴은 언제???**
        - 구상형이 확장가능성이 있으면서
        - **개별로직들이 공통점이 없어서 -> 1개 메소드로 인터페이스를 정할 때**
    - **만약, 공통로직이 존재하여 템메패턴써야하는데, 전략패턴을 써버린다면?**
        - **공통로직 -> 수정시 일일히 다 수정해야함.**
    - **한정된 추메패턴에서, 구상체가 추가되었는데, 공통로직이 안보인다? 전략패턴으로 싹다 다시 바꿔줘야한다.**

  ![image-20220623225344143](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623225344143.png)

- 또한, 내부에 **`정책조건을 빈컬렉션 필드에 모아서`, 할인 정책 적용시 사용한다.**

    - **생성자에서 받지 않고, 이후에 유연하게 받아들일 수 있게 setter처럼 받기기능을 제공**한다.

  ![image-20220623225407203](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623225407203.png)

- 특이하게 **같은 policy를 받아서, 내부 `정책조건들 필드`을 복사해서 가져오는 기능도 존재한다.**

    - **이는, movie에 적용될 policy가 다른 policy로 대체될 때, `기존 policy의 정책조건을 가져와서 물려받을 수 `있게 만든다.**

        - AmountPolicy에서 PercentAmount로 할인정책을 바꿀 때, 기존 AmountPolicy가 가진 conditions를 물려받는 기능

    - **다시 conditions객체를 만들어 넣으면 안되나?**

        - 객체는 식별자로 판단되기 때문에, 다시만들면 다른 객체가 된다.
        - 같은 종류의 객체비교는 내장을 깔 수 있기 때문에 쉽게 가능해진다.

      ![image-20220623225318562](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623225318562.png)

    - 이런 방식을 택한 이유는
        - **Movie의 지식을 줄이기 위해,** policy만 알고, policy가 conditions를 알게 하는 방식으로 변경
        - 기존: SequenceAmountPolicy로 둘다 적용된 조건+정책 구상체를 받아들였었다.
        - 원래는 Movie가 가졌던 condition들인데, policy가 가지게 하였다.
            - policy교체시 -> 딸린 conditions들도 옮겨줘야하는 책임을 가지게 된다.
            - **객체에서는 이렇게 소유권을 맘대로 옮겨선 안된다.**



##### AmountPolicy, PercentPolicy

![image-20220205164846747](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220205164846747.png)

- 템플릿메소드패턴의 구상체들은, **훅메서드**만 개별구현해주면 된다.
    - 추상클래스에서 안보이지만 다 물려준다.
    - 훅메서드 구현시 혹시, 다른 정보가 필요하면?, 생성자에서 받아서 처리해주면 되는데, 부모 생성자도 super로 재정의해서, 부모레벨에서 다 처리되고 내려오게 하면 된다.
- 할인요금을 계산할 때, 넘어오는 fee에 대해, **얼만큼의 amount만큼 깎아줘야하는지 정보가 미리 태어날 때 정해져있어야한다.**
    - 값객체 amount를 생성자에서 받는다.

![image-20220205165027756](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220205165027756.png)





##### DiscountCondition

![image-20220205170231321](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220205170231321.png)

![image-20220623235407537](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220623235407537.png)

- 기존에 가지던 action메서드는 DiscountPolicy에게 위임하고 1개의 메서드를 갖는 인터페이스가 되었다. 전략패턴



##### Period(Time)Condition, SequenceCondition

![image-20220205170250233](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220205170250233.png)
![image-20220205170320843](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220205170320843.png)

- 정책조건의 전략객체로서, 개별적으로 조건을 만족하는지 구현한다.
    - 필요한 내부정보는 생성자에서 조달한다



##### TicketSeller

![image-20220205170435907](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220205170435907.png)

- reserve()의 **인자(파라미터)가 5개**나 된다.

    - **객체를 메서드의 인자**로 받았다 -> 그 객체를 알고 의존하는 **dependency를 가지고 있다.**

        - 5개의 묶음을 형(type, class)로 강제하면 좋으나 **상위도메인이자 소속기관인 ticketOffice에게  customer를 제외하고 4개의 인자를 다시 넘겨줘야한다.**

          ![image-20220624000603189](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220624000603189.png)

        - 또한 customer는 예매시, seller인 나에게 5개의 정보를 다 넘겨준다.

- **나는 비록  5개의 인자를 받았지만, 이 dependency 중 `theater, movie, screening의 객체`을 `나(seller)는 사용하면 안된다`**

    - **왜?**

        - **나는 ticketOffice에게 `정보 전달의 책임(의존성을 중개만)`만 있다.**

    - **원래 의존성을 가지던 ticketOffice만 사용해야한다.**

    - **`원래 의존성이 없었는데, 메서드의 인자로 넘어온 의존성`에 대해서는 `그 의존성을 사용하고 끝낼 것이 아니면 사용하면 안된다.`**

        - 보통 받으면 사용하면 되지만, **seller는 해당 `의존성의 전달 책임만 있고, 사용하고 끝낼 놈은 ticketOffice`다 **
        - **`내가 사용하고 끝낼려면, ticketOffice에게 전달하면 안된다.`**

    - **현재 코드에서 오류 잡아보기**

        - 인자로 받은 의존성을, 내가 직접 사용하는 `movie`

          ![image-20220624001326990](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220624001326990.png)

        - **이미 사용했지만, 내가 의존하는 객체에 다시 넘겨주는 오류**

          ![image-20220624001414341](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220624001414341.png)



- **이렇게 전달만 할 것인지 vs 직접 사용할 것인지를 선택하는 것은 `디미터원칙(최소지식 원칙)`을 지키려고 하는 것이다.**

  ![image-20220205171559206](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220205171559206.png)

    - `빨간줄`이 디미터 원칙을 위배한다는 것을 알아야한다.
        - **ticketOffice에게 넘겨줘야할 것인데, 중간에 직접 사용하는 오류**
        - **내가 사용하지말고, 나와 직접적인 관계가 있는 1차원적 관계의 `ticketOffice에게 책임을 위임`한다.**
            - my) 그대로 놔두면, 나도 사용, office도 사용 -> **movie입장에서는 적어도 1가지는 디미터원칙 위반(건너고 건너는?)**
            - movie가 변하면 -> seller(나)도, office도 바뀐다
    - **나와 1차원 관계가 있는, 직접적인 관계의 ticketOffice에게 movie를 사용하는 것을 위임하자**

- **`의존성(movie)의 변화는 한 곳`에서만 나타나도록 `1차원 적인 놈에게만 의존성 사용을 몰빵`하자. 여러 객체가 쓰지말자.**

    - **`메서드의 인자로 받아도, 직접 사용하지 않으면 의존관계가 없다`**

    - **ticketOffice**는 **원래 movie를 전달받아 사용했었다.**

        - **이미 메서드인자로 전달받은 객체를 내부에서 사용** -> 직접적인 관계, 1차원적인 관계, **의존성 가진 상태**

      ![image-20220624002339096](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220624002339096.png)

    - **이왕 받는 거, 미리 한번 더 받아서, 책임을 위임받는다.**

        - ticketSeller 코드변화

            - **직접의존성이 있는 객체가 대신 기능해주는 것을 wrapping method라 한다.**

          ![image-20220624002452842](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220624002452842.png)



#### TicketOffice

![image-20220205172319775](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220205172319775.png)

- seller가 직접 호출해서 의존성이 추가되는 것을 방지하기 위해 office는 **이미 직접 의존성을 가지므로, 대신 기능해주는 `wrapping method`를 제공했다. **

- **`내부에 코드(메서드)가 추가되었을 때, 항상 기존 코드와 비교해서 <메서드가 대신 차지할> 중복코드는 없는지 확인`해야한다.**

    - 빨간색의 코드가 중복이다. 추가된 메서드로 대체한다.

  ![image-20220624003633243](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220624003633243.png)


##### Main

- 한번에 policy + condition 합쳐진 구상체에서 -> condtions를 채워서 가지고 있는 policy로 사용법 변화되었으니 코드 수정

  ![image-20220624005647656](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220624005647656.png)
