package theater.discount;

public interface DiscountPolicy {
    interface AMOUNT extends DiscountPolicy{}
    interface PERCENT extends DiscountPolicy{}
    interface COUNT extends DiscountPolicy{}
    interface NONE extends DiscountPolicy{}
}
