package theater.discount.policy.factory;

import theater.discount.policy.strategy.Calculator;
import theater.discount.policy.strategy.NosaleCalculator;
import theater.domain.Money;

public class NosaleCalculatorFactory implements Calculator {
    private NosaleCalculator cache;

    private synchronized Calculator getCalculator() {
        if (cache == null) {
            cache = new NosaleCalculator();
        }
        return cache;
    }

    @Override
    public Money calculateFee(final Money fee) {
        return getCalculator().calculateFee(fee);
    }
}
