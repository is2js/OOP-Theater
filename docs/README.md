### Description
- object book의 Theater 예제의 상호의존성을 수정한 설계도대로 객체class를 하나씩 만들어간다.
- `step1` : Client코드부터 짜야하지만, 상호관계를 파악하기 위해 class들을 순서대로 설계한다.
- `step2` : step1과 거의 별개의 theater를 새로 설계한다. 티켓 대신 예매권을 사용하고, 할인 정책을 2개의 인터페이스로 반영한다.
- `step3` : step2에서 할인정책 부분이 변경되며, SOLID 원칙을 반영하려함.
- `step4` : step3에서 전략패턴에 생성사용패턴(Factory -> 위임된 Factory)를 적용한다.

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

### STEP3(정책조건은 전략패턴 + 정책은 템플릿메소드패턴으로 할인정책 반영)
- step2에서 SOLID원칙을 적용하려고 한다.
- movie가 condition + policy 모두 아는 최종 정책객체를 받아서 사용하던 책임을 
    - movie는 템플릿메소드패턴이 적용된 policy만 알도록 책임 감소
    - policy가 전략패턴이 적용된 condtions들을 내부에서 가져 알도록 책임 위임 되었다.

![image-20220624005906639](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220624005906639.png)
- `DiscountCondition`은 전략패턴으로 변경
    - trigger메서드 1개만 가지므로
- `DiscountPolicy`는 추상클래스의 템플릿메소드 패턴으로 변경
    - condtions들을 담을 `공통 field` 필요함. 
    - 공통 field coditions를 채울 `공통 public 템플릿메소드 여러개`가 필요함.

### STEP4
- step3에서 전략객체를 pushed로 강제주입 받는 DiscountPolicy에 생성사용패턴을 적용하기 위해
    1. 전략패턴에 simple Factory 적용하기
    2. simple Factory를 위임된 Factory로 변경하기
