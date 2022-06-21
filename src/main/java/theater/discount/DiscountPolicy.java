package theater.discount;

public interface DiscountPolicy {
    //1. 인터페이스 내부에서 자식인터페이스를 둘 때는, extends에 중괄호로 구현까지 해서 상속시켜야한다.
    interface AMOUNT extends DiscountPolicy{}
    interface PERCENT extends DiscountPolicy{}
    interface COUNT extends DiscountPolicy{}
    //2. 정책의 종류에는 NONE도 포함시킨다.
    interface NONE extends DiscountPolicy{}
}
