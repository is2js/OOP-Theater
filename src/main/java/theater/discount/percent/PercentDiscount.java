package theater.discount.percent;

import theater.domain.Money;
import theater.discount.DiscountCondition;
import theater.discount.DiscountPolicy.PERCENT;

public abstract class PercentDiscount implements PERCENT, DiscountCondition {
    private double percent;

    protected PercentDiscount(final double percent) {
        this.percent = percent;
    }

    @Override
    public Money calculateFee(final Money fee) {
        return fee.minus(fee.multi(percent));
    }
}
