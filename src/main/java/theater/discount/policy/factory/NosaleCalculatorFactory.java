package theater.discount.policy.factory;

import theater.discount.policy.strategy.Calculator;
import theater.discount.policy.strategy.NosaleCalculator;

public class NosaleCalculatorFactory implements CalculatorFactory {
    private NosaleCalculator cache;

    @Override
    public synchronized Calculator getCalculator() {
        if (cache == null) {
            cache = new NosaleCalculator();
        }
        return cache;
    }
}
