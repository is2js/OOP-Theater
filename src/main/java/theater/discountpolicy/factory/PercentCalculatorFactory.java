package theater.discountpolicy.factory;

import java.util.Set;
import theater.discountpolicy.factory.condition.DiscountCondition;
import theater.discountpolicy.strategy.Calculator;
import theater.discountpolicy.strategy.PercentCalculator;
import theater.domain.Money;

public class PercentCalculatorFactory implements PolicyFactory {
    private PercentCalculator cache;
    private Double percent;
    private Set<DiscountCondition> conditions;

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

    @Override
    public Set<DiscountCondition> getConditions() {
        return conditions;
    }

    public void addCondition(final DiscountCondition condition) {
        this.conditions.add(condition);
    }
}
