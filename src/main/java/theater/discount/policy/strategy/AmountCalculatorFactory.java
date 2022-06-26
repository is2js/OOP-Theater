package theater.discount.policy.strategy;

import theater.domain.Money;

public class AmountCalculatorFactory implements Calculator {
    private AmountCalculator cache;
    private Money amount;

    public AmountCalculatorFactory(final Money amount) {
        this.amount = amount;
    }

    private synchronized Calculator getCalculator() {
        if (cache == null) {
            cache = new AmountCalculator(amount);
        }

        return cache;
    }

    @Override
    public Money calculateFee(final Money fee) {
        return getCalculator().calculateFee(fee);
    }
}
