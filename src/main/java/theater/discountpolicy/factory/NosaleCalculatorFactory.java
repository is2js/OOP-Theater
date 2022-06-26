package theater.discountpolicy.factory;

import java.util.Set;
import theater.discountpolicy.factory.condition.DiscountCondition;
import theater.discountpolicy.strategy.Calculator;
import theater.discountpolicy.strategy.NosaleCalculator;
import theater.domain.Money;

public class NosaleCalculatorFactory implements PolicyFactory {
    private NosaleCalculator cache;
    private Set<DiscountCondition> conditions;

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

    @Override
    public Set<DiscountCondition> getConditions() {
        return conditions;
    }

    public void addCondition(final DiscountCondition condition) {
        this.conditions.add(condition);
    }
}
