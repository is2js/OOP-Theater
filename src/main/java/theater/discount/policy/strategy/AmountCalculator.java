package theater.discount.policy.strategy;

import theater.domain.Money;

public class AmountCalculator implements Calculator {
    private Money amount;

    public AmountCalculator(final Money amount) {
        this.amount = amount;
    }

    @Override
    public Money calculateFee(final Money fee) {
        return fee.minus(amount);
    }
}
