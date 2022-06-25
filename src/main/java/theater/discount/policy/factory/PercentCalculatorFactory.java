package theater.discount.policy.factory;

import theater.discount.policy.strategy.Calculator;
import theater.discount.policy.strategy.PercentCalculator;

public class PercentCalculatorFactory implements CalculatorFactory {
    private PercentCalculator cache;
    private Double percent;

    public PercentCalculatorFactory(final Double percent) {
        this.percent = percent;
    }

    @Override
    public synchronized Calculator getCalculator() {
        if (cache == null) {
            cache = new PercentCalculator(percent);
        }
        return cache;
    }
}
