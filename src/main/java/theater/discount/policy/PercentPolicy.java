package theater.discount.policy;

import theater.domain.Money;

public class PercentPolicy extends DiscountPolicy {
    private Double percent;

    @Override
    protected Money calculateFee(final Money fee) {
        return fee.minus(fee.multi(percent));
    }
}
