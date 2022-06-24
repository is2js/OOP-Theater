package theater.discount.policy;

import theater.domain.Money;

public class AmountPolicy extends DiscountPolicy {
    private Money amount;

    public AmountPolicy(final Money amount) {
        this.amount = amount;
    }

    @Override
    protected Money calculateFee(final Money fee) {
        return fee.minus(amount);
    }
}
