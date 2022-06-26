package theater.discount.policy.strategy;

import theater.discount.policy.strategy.Calculator;
import theater.discount.policy.strategy.PercentCalculator;
import theater.domain.Money;

public class PercentCalculatorFactory implements Calculator {
    private PercentCalculator cache;
    private Double percent;

    public PercentCalculatorFactory(final Double percent) {
        this.percent = percent;
    }

    private synchronized Calculator getCalculator() {
        if (cache == null) {
            cache = new PercentCalculator(percent);
        }
        return cache;
    }

    @Override
    public Money calculateFee(final Money fee) {
        return getCalculator().calculateFee(fee);
    }
}
