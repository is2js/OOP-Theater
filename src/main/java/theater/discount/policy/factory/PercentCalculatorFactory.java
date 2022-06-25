package theater.discount.policy.factory;

import theater.discount.policy.strategy.Calculator;
import theater.discount.policy.strategy.PercentCalculator;
import theater.domain.Money;

public class PercentCalculatorFactory implements CalculatorFactory {
    private PercentCalculator cache;
    private Double percent;

    public PercentCalculatorFactory(final Double percent) {
        this.percent = percent;
    }

    public synchronized Calculator getCalculator() {
        if (cache == null) {
            cache = new PercentCalculator(percent);
        }
        return cache;
    }

    @Override
    public Money calculateFee(final Money fee) {
        throw new UnsupportedOperationException("PercentCalculatorFactory#calculateFee not implemented.");
    }
}
