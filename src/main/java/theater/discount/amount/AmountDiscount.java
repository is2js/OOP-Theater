package theater.discount.amount;

import theater.Money;
import theater.discount.DiscountCondition;
import theater.discount.DiscountPolicy.AMOUNT;

public abstract class AmountDiscount implements AMOUNT, DiscountCondition {
    private Money amount;

    protected AmountDiscount(final Money amount) {
        this.amount = amount;
    }

    @Override
    public Money calculateFee(final Money fee) {
        return fee.minus(amount);
    }
}
