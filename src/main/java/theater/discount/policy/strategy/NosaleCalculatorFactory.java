package theater.discount.policy.strategy;

import java.util.Set;
import theater.discount.condition.DiscountCondition;
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
