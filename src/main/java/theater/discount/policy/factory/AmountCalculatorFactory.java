package theater.discount.policy.factory;

import theater.discount.policy.strategy.AmountCalculator;
import theater.discount.policy.strategy.Calculator;
import theater.domain.Money;

public class AmountCalculatorFactory implements CalculatorFactory {
    private AmountCalculator cache;
    private Money amount;

    public AmountCalculatorFactory(final Money amount) {
        this.amount = amount;
    }

    @Override
    public synchronized Calculator getCalculator() {
        if (cache == null) {
            cache = new AmountCalculator(amount);
        }

        return cache;
    }
}
