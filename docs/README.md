### STEP1

- 책(error 빨간색): 잘못된 객체지향 설계

  ![image-20220122214003688](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220122214003688.png)

- 수정본

  ![image-20220122214556326](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220122214556326.png)
  ![image-20220621120453611](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621120453611.png)

    - 받아오되, **필드객체로 값으로 저장**하고 있어야 의존성이 보인다.
        - 메서드인자로 받아오되, 그 class를 필드값으로 저장없이 다른 것을 위해 사용만 한다면 의존성은 보이지 않는다.

- **theater(시작점)**는
  ![image-20220122223342652](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220122223342652.png)

    - <-> 자신이 가진 가격**정보를 넘겨주어**, **만들어 파는(**찍어낼) ticket을 **서로 안다**

        - 내부 발행(생성)객체는, **반드시 this로 정보를 줘서**, **현재 발행 주체를 객체=식별자로 알려주면서**가 정보를 제공하여 생성하게 한다.

            - **theater.fee의 필요한 값만 넘겨주어선 안된다. 객체는 식별자로 이야기한다.**
            - class내 생성이므로, 여러 인스턴스가 각자의 정보를 가지고 있기 때문에, 인스턴스 theater마다 서로 다른 정보를 가지고 있고, 서로 다른 정보의 Ticket이 발행된다.

        - 내부 메서드안에서 new Ticket( this:현재인스턴스의 정보 )로 바로 생성할 재료객체를 앎.

            - **정보를 this로 현재instance의 정보를 제공하여 내부 생성객체는 서로 안다고 표현**한다.

              ![image-20220620220809177](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620220809177.png)

        - 자신이 만든 ticket을 office에게 맡기니

            - office는 -> ticket을 안다
                - 재료객체를 받은 것은 안다고 표현

        - office는 받은 ticket을 seller에게 표 팔기 위해

            - seller -> ticker을 앎
                - 받은 것을 안다고 표현

    - -> 내가 **`내부`에서 만든** ticke **재료객체를 `발행과 동시에 받아갈`(줄)** N개의 `office`(들)을 **안다**

        - **내부 생성객체를 받아갈 객체(들)**는 **외부에서 인자로 들어온** 뒤, **자기가 add메서드기능 제공해서 받아가야하므로 안다고 표현**한다.

            - **뭔가를 받는 놈도, 받을 준비가 되어있어야한다( 받는놈.add( ), 받는놈.set( ))**
            - 정해진 수를 받아갈 땐, 받을놈 + 그 갯수를 함께 인자로 받는다.
            - 안 상태 = 인자로 받아온 상태에서, 들어온 객체의 기능을 통해,  내가 가진 재료객체를 건네줄 수 있다.
                - 받아온 객체.add( 재료객체 ) 형식으로 객체에게 건네준다.
            - 받아온 객체 목록은 List필드값으로 저장해서, 확인용으로 쓸 수 있다.

          ![image-20220620221101632](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620221101632.png)

    - -> 자기가 발행하는 내부(발행)생성객체 invitation을 서로 안다

      - 내부 발행객체는, 현재instance의 정보를 받아가므로this를 건네준다.

        - 여러 개를 받아가면, add기능을 제공받고
        - 1개만 받아가면, set기능을 제공받자

      - 내부 생성객체는, **메서드인자를 통해 들어와** 자기의 기능으로 받아가야하므로,  안다고 표현

        ![image-20220620223020232](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620223020232.png)

    - -> 자신이 발행하는 invitation을 **발행과 동시에 받아갈** audience를 안다

      - 내부 생성객체를 넘겨받을 객체는, **인자로 들어와서** 받을준비=지 메서드로 받아갸므로,  또한 안다고 표현

        ![image-20220620222121228](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620222121228.png)

    - ->  자신의 기능(.enter( ))의 대상인 audience를 안다

      - **(여기선 검증) 기능의 대상 객체를 메서드의 인자로 받아와야하기** 때문에 안다고 표현

        - theater.enter(  audience )
        - **Ticket자체로도 .isValid()를 확인하는 기능을 할 수 있지만(객체세상)**
          - 하지만, **theater 인스턴스마다 서로 다른 정보로 발행을 하니, 발행한 theater정보가 필요하므로, 발행시 정보를 준 this가 필요하다**

        ![image-20220620222944995](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620222944995.png)

- **ticket**(시작점theater이 아는 3 중 1)은
  ![image-20220123210927966](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220123210927966.png)

    - <-> theater가 ticket을 발행(안다)할 때, **현재 theater instance(발행주체)로부터 정보를 받아서 생성**하므로 theater를 서로 안다

        - **생성자에서 외부 인자로 받아올 때** 안다고 한다.

            - **특히, 발행주체는 fee정보 대신 불변하도록 final field로 가지고 있는다.**

          ![image-20220620230053646](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620230053646.png)

        - 추가

            - **getFee**(): 정보를 **객체로 받아왔다면, 객체내부정보를 getter를 통해 중개해서 제공**한다.

                - final로 불변이지만, 발행주체의 정보(원시값  fee)가 변하더라도, 원시값(fee)을 포장한 (theater)를 통해 정보를 가져온다
                -   `.`을 통한 포인터의 포인터 정보는 **해당 값이 바뀌어도, 실시간 변화된 정보를 받아온다.**
                - **값의 변화가 반영되려면, 참조의 참조로 얻어와야한다! 그래야 참조의참조 업데이트가 runtime에 반영(이후에도 반영)**
                    - **내 고유정보가 아니면, 값으로 받아오지 말자!**
                    - **포인터의 포인터를 비용으로 치루더라도 -> 변화가 바로 반영되도록 하는 원리를 가지게 한다.**

              ![image-20220620230537223](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620230537223.png)

            - **isValid**(Theater theater):

                - **발행되는 `소모되는 재료객체`로서 `있다/없다 존재 확인이 필요할 때 null객체`**를 가지고 있는다.

                - **이미 쓴 표**인지 검증할 때, flag변수를 상태값으로 가지고 있는다.

                - 그외에, **발행주체**에 대한 검증 + **잘못 생성**된 null객체인지도 추가한다.

                    - **발행되는 재료객체로서 null객체를 가진다.**

                  ![image-20220620231924384](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620231924384.png)



- **Invitation**(시작점theater이 아는 3 중 2)은
  ![image-20220123213008079](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220123213008079.png)

    - <-> theater가 invitation을 발행(안다)할 때, **현재 theater instance(발행주체)로부터 정보를 받아서 생성**하므로 theater를 서로 안다

        - **생성자에서 외부 인자로 받아올 때** 안다고 한다.
            - **특히, 발행주체는 fee정보 대신 불변하도록 final field로 가지고 있는다.**

      ![image-20220620232447385](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620232447385.png)

    - 추가

        - **발행되는 `소모되는 재료객체`로서 `있다/없다 존재 확인이 필요할 때 null객체`**를 가지고 있는다.

- **office**(시작점theater이 아는 3 중 3)는
  ![image-20220123214826683](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220123214826683.png)

    - -> **받는** ticket을 안다

        - **메서드의 인자로 받는 재료객체**를 (저장도 해야하니) 안다고 표현

            - 받을 때는, **주는 객체한테 들어가서  받는 기능을 제공해야만 한다. 이 때, 재료객체를 인자**로 받는다.

          ![image-20220620233026335](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620233026335.png)

    - (심화) 의외로  **주는 theater를** **모른다.**
      ![image-20220620233817178](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620233817178.png)
      ![image-20220621002935330](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621002935330.png)

        - office는 **주는 주체는 모른 상태에서** 받는 기능만 제공한다.
            - my) **주는 주체 내부로 들어가 받는 기능만 제공할 땐, 주는 쪽을 몰라도 된다.**
                - 넘어가 기능 제공하는 쪽은 몰라도 된다.
        - office는 theate에게 **주는 것이 없기 때문에, 인자로 받아올 필요가 없다**.
            - 화살표 방향: 주는쪽이, **줄때는 받는 쪽을 알고서 인자로 받아와, 자기 기능으로 받아가게 하여**, 준다.
            - **이 때, 받는 쪽이, 들어와서 자기 기능을 open(제공)해 줄때만 주는 것이 가능하다**
                - **화살표 나가는 쪽이 많이 알고 있고, 줄 수 도 있지만, 받는 쪽이 기능 제공(오픈)해줄 때만 줄 수 있다.**
                    - 거만한 것은 화살표를 받는 office다. 주는데도 안받을 수 있으니
            - 화살표 나간다 = 알고 있다 = 줄 수 있다?(X) = 알고서 인자로 받아왔지만, 받은 객체 =  **`화살표 받는 쪽의 받기 기능`을 제공한다면, 이용할 뿐이다.**
            - 화살표 받는다 = **화살표 쏜놈이 나를 받아or생성해서 쓰려고하여, `화살표 받는 쪽으로서 기능을 제공`한다.**

    - <- 가지고 있는 ticket을 팔라고  **seller에게 `주는 데도, 역으로 안다.`(= 주는 객체들이 이미 완성된 것이니, 니 상황에 맞게 빼가라고 기능 제공할테니, 나를 알아서 가져가, 기능 이용해)**

        - **준다고해서, 화살표가 나가면서 알아야하는 것은 아닌가보다. **

            - **줄 것이`내부 생성해야하는 객체라, 내부에 와서 받아감`으로 줘야할 때는 화살표가 나가서 알고 (Theater -> Office)**
            - **줄 것이 `이미 생성된 객체라,  외부에서 빼갈 때`는, 화살표를 반대로 받아서  `상대방이 알고서 받아가 빼가는 제공 기능을 이용하도록` 빼가는 기느응ㄹ 제공해서 준다.(Office <- Seller)**
                - theater는 -> office에게  **내부발행해야하는** ticket을 **받아가라고 할** 때, 화살표가 나가면서 알고 있어야만 = 받아와야만 했다.

        - **office는 -> seller에게 ticket을 줄 때(이미 완성된 객체라 seller상황에 맞게 빼갈 때)**

            - seller를 받아와, seller의 받는 기능을 이용해서 주는게 아니라
            - seller가 **이미 완성되어 가지고 있는** tickets를 **알아서 빼갈 때**는 seller가 office를 받아가서(알고서), 내부 재료객체(tickets)를 `빼가는 기능`을 **seller내부 상황에 맞게 빼간다.**
                - seller상황에 맞게 빼가는 기능이니, **빼가는 놈의 상황에 맞는 여러 빼가는 기능을 제공**할 수 도 있다.
                    1. 초대권을 가진 audience을 만난 seller의 상황: 무료로 빼가게 한다.
                    2. 일반 audience를 만난 seller의 상황: 돈 주고 빼가게 한다.
                - 빼가는 기능을 상황별 제공할 때, invitation여부를 몰라도 되나??
                    - invitation를 받아가는 audience도 알아야한다.
                    - **상황별 빼가는 기능만 제공하고, 상황 판단은 빼가는 놈인 seller에서 분기별로 알아서 결정해서 기능을 쓰게 한다.**

          ![image-20220621002330351](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621002330351.png)

        - my) 정리
          ![image-20220621002926657](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621002926657.png)

            - 발행주체가 내부 생성 재료객체를, 받아가라고 할 땐, 화살표가 나간다
                - 받아가는 기능을 제공하는 놈을 알고서, 메서드 인자로 받는다.
            - 갖다 쓴놈이, 창고에서 완성된 재료객체를, 빼갈 때도, 화살표가 나간다
                - 상황에 맞는 **빼가는 기능을 제공하는놈을 알고서**, 메서드 인자로 받는다.
                - **결과적으로 빼가는 기능을 제공하는 office는 seller의 소속기관으로서 seller의 `setter의 메서드 인자로 받아서 알게 된다.`**
                    - **즉, 빼가는 기능을 이용하는 상황의 메서드에서 인자로 받는게 아니라, 미리 알게 된다.**
                - **소속기관이면, 생성시부터 정해지니, `생성자의 인자 -> final로 박아`두면 안될까 싶지만, `소속기관은 불변하지 않고 바뀔 수 있어서, setter -> 정보field로 박아둔다.`**

    - 추가

        - ticket과 다르게, **생성재료 정보를 가진, 발행주체 객체(Theater)가 따로 있는게 아니라면, 생성시 필요한 재료를 `값`으로 받아 필드로 가지고 있는다.**

          ![image-20220620234825699](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620234825699.png)

        - 받는 기능 제공시, 여러 번 받아와 저장한다면, 저장필드로 빈 리스트 필드를 가진다.

          ![image-20220620233051642](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220620233051642.png)



      - 외부에서 재료객체(ticket)을 빼가기도 하는데(By seller), 빼가기 전에 그 객체의 정보를 먼저 조회하기도 한다

        ![image-20220621011903391](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621011903391.png)



- **TicketSeller**는
  ![image-20220124012340287](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220124012340287.png)
  ![image-20220621003304340](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621003304340.png)

    - -> **office에서** 돈을주고 **빼온(사온) 재료객체** ticket을 안다(모르고 팔 순 없다)

        - **알고서, 창고객체(office)의 기능을 이용해 `빼온 재료객체`를 return값으로서**를 안다고 표현

    - -> office에게 **고용되는 소속기관 = 상위도메인으로서** 안다. **+ 재료객체를 빼오는 기능제공 대상을** 안다

        - **`프리랜서 소속기관으로서 setter의 인자로 받아와 정보필드에 박아둘 프리랜서 소속기관, 상위도메인 객체`**를  안다고 표현

            - **`소속`을 `setter로 받을 것인지 / 생성자에서 받을 것인지`는 신중하게 생각해야한다.**
                - **태어나서 `불변하는 발행주체, 소속기관` -> `생성자에서` 태어날때부터 받기**
                - **`중간에 계속해서 바뀔 수 있는 프리랜서의 소속기관` -> `setter`로 받기**

          ![image-20220621005830062](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621005830062.png)

        - **(창고로부터)` 빼오는 기능을 제공하는 놈을 받아와, 상황별로 빼오는 기능을 이용`해야해서 안다고 표현**

    - -> **재료객체** ticket을 **거래할(받아갈) 거래대상** audience(고객)을 **서로 알아야한다.**

        - 서로알기

            -  발행주체(정보보유, theater) <-> 발행재료객체(Ticket)
            - 거래대상: seller <-> audience
                - **seller: 빼가는 기능 제공시, `audience를 알고서 거래가능한지  검증 -> 물건양호시 사는놈의 돈 차감`까지 해야한다**
                - **audience: 들어가서 검증을 당하기도 하지만, 최종적으로 seller를 알고 seller가 거래물건을 주는 기능을 이용한다.**

        - 메서드명만 getTicket(거래 대상)이지, 거래메서드다.

        - 받는 놈기만 하는 놈(theater-office)은 갑으로서 받는 기능을 제공하기도 하지만, **거래에서는 사는 객체(audience)는 파는 객체(seller)에게 메소드 인자로 들어와, `거래가능한지 검증 -> 물건양호시 돈 차감까지`당해야해서** 알아야한다.

            - **거래를 하는 상대객체**를 서로 안다고 표현(**트랜잭션 관계**)

          ![image-20220621012307689](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621012307689.png)

    - (심화) theater-office의 관계에서 **받기 기능을 제공하는 받아가는 office가 갑**이었다면

        - **여기(seller - audience) 거래관계에서는 `받기기능이 없이, 검증당하고, 돈차감당하는 audience`보다, `최종적으로 getter의 return으로 거래재료객체를으로 건네주는 기능을 가진 seller가 갑`이다.**

            - 최종 기능을 제공하는 쪽이 갑이다.
            - audience는 거래정보를 검증당하고, 돈을 차감당하는 기능만 제공하는데, **최종적으로 중요한 기능은 seller의 거래물건 제공기능이며, 그 안에서 audience가 들어와 검증/차감 당한다.**
            - 파는놈 seller는, 사는 놈인 audience를 메소드 인자로 받아서 **자기 기능으로 audience에게 거래 검증/돈차감기능을 활용하여  return으로 거래객체를 줄지/안줄지를 결정하는 기능을 제공해서 갑이다.**

        - my) **거래관계에서 Seller와 Audience는 서로 알지만, 파는놈 Seller가 갑으로서 `사는 놈.buy기능( 파는놈 )`의 인자로 들어가지만, seller에게 제공하는 기능이 아니며, 그 내부에서는 `사는놈(을)이 seller(갑)의 .getTicket( 파는놈this )의 거래메소드의 인자`로 들어가서, 돈검증/물건양호시 돈차감까지 당한다.**

            - 아래서 할 내용

              ![image-20220621014243063](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621014243063.png)

- audience는
  ![image-20220124223050202](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220124223050202.png)
  ![image-20220621105608176](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621105608176.png)

    - -> **거래하여 받아와 나한테 저장하는 재료객체** ticket의 존재를 미리 안다

        - 거래후 받아와 **내 상태(field)로 저장(setter)해야한다면, 객체필드로서 미리 알아야한다.**

      ![image-20220621110334766](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621110334766.png)

        - (심화) 소모성 재료객체를 상태(객체필드)로 가질 땐, **setter전 default상태를** null객체를 사용한다.

    - -> 상대방이 줘서 상태로 저장하는 객체 Invitation은 미리안다.

        - **setter** 역시 **받기기능으로, 받기후 내 객체필드로 저장하려면, 미리 알아야**한다.

        - (심화) **받기기능으로 받을 준비가 되어있는 객체는, 주는 놈이 일방적으로 알기만 하면 된다.**

            - my) **받기기능 제공 -> 주는놈이 setter or add로 일방적으로 알아서 꽂아주기만 한다.(소통은X)**
            - theater -> ticketoffice
            - theater -> audience

          ![image-20220621111556554](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621111556554.png)

    - -> ticket을 **판매하는 주체이자 거래대상은** seller를 **서로안다**

        - 파는놈이 갑으로서 **팔기 기능을 제공**하니, **알고서 받아와 기능을 이용**해야한다.
        - (심화)**사는놈은, 스스로 구매 기능을 가지나(제공하는게 아님), 파는놈을 인자로 받아와, 파는놈의 기능을 이용해서 물건을 받아와 저장한다**
            - 파는놈의 기능 내부에는, 사는놈을 검증/돈차감 해야하기 때문에 인자로 받는다.

      ![image-20220621111753408](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621111753408.png)

        - 거래해서 받아오는 객체(Ticket)뿐만 아니라 파는놈(seller) 역시 알아서 그 기능을 이용해야한다.

    - 추가

        - 고객(사는놈)은 태어날때부터 값 amount(돈)를 자신의 상태로서 가지고  태어난다.

          ![image-20220621105403648](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621105403648.png)

        - 물건을 사는 놈은, 파는 놈이 알고서 이용할 기능 2개를 제공해야한다.

            1. 거래가능한지 검증당하기 hasAmount
            2. 물건양호시 돈차감당하고 성공여부 minusAmount

          ![image-20220621114252207](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621114252207.png)

            - (심화) seller(갑)에 의해, 검증/돈차감에 대한 최소한의 노출(boolean)만 해준다.
                - getAmount로 내 정보를 직접 가져가서 하는 것이 최고의 노출이며 안좋은 것이다.

        - (심화)**갑이 파는놈(seller)가 요구하는, `검증`(거래가능한지 검증, 초대권 잇는지 검증) 및`차감후 성공여부`(돈 차감, 초대권 차감)**메서드들은 **파는놈(seller, 갑)이  사는놈(audience, 을)에게 요구하는 스펙들(조건들)이며 `거래(트랜잭션)을 위한 조건들`이다..**

            - **hasAmount**(거래가능한지 물어봄), **getInvitation**(직접 정보가져가서 검증)
            - **minusAmount**(돈차감후 성공여부), **removeInvitation**(성공여부 없이 바로 초대권 차감만)
                - **`트랜잭션 조건들`만, `인터페이스(카테고리)로 빼서 책임을 분리`할 수 도 있다.**
                - TransactionCondition
                    - 어렵다..

        - 상대방이 나에게 시키지 않고, 내 정보를 물어보고 내 외부에 직접 판단/검증할 때 getter주기기능을 제공한다.

            - ~~최고수준의 노출이라 딱히 좋다고 할 수 없다.~~
            - **my) 내생각에는 `NULL객체`로 바로 확인 가능할 경우, 직접 가져가서  != Null객체 으로 바로 확인가능하니까, 시키지 않고 받아가는 것 같다.**

          ![image-20220621113614284](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621113614284.png)

        - (심화) 사는놈은 **스스로 사용하는 기능은 buyTicket밖에 없고**

            - 다른 메서드들은 전부, 다른 클래스들을 접대(기능제공)해주는 메서드들 밖이다.
                - setter도 **상대방이 일방적으로 알고서 꽂아주는 것**이기 때문에, 상대방용이다.  상대방이 안다 = **상대방 내부에서 사용**한다.
                - getter도 **상대방이 정보를 직접 받아가 상대방 내부에서 사용**한다.
                - 4개의 트렌잭션 스펙들은 **seller가 알고서, 자기내부에서 이용**하는 것이다.

          ![image-20220621115106476](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621115106476.png)

            - 알고서 필드를 가져오되, 상태값(필드)로 해당class 객체를 저장하지 않으면, 의존성은 안보인다.

              ![image-20220621120809980](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621120809980.png)

- main은

  ![image-20220621124740309](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220621124740309.png)

    1. **원래 Main부터 짜야한다. 절대 class -> Main 순으로 짤 순 없다.**
    2. 내 도메인에 맞는 **시나리오부터 main에서 짜면서**
        1. **객체들의 협력상황을 보면서**
        2. **class들을 만들어나간다.**
    3. 테스트코드부터 설계에 반영하려면, **테스트코드가 실제 설계에 사용되는 client코드와 닮아있어야**하는데
        - **클래스를 완성되었다고 가정**하고, **클래스를 객체로 어떻게 사용할 건지 먼저 짜보는게** 훨씬 낫다
        - **상상속으로  class를 짜봤자, 어떻게 사용되는지는 모른다.**
