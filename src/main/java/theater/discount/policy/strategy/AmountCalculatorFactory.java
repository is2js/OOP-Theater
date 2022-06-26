package theater.discount.policy.strategy;

import java.util.HashSet;
import java.util.Set;
import theater.discount.condition.DiscountCondition;
import theater.domain.Money;

public class AmountCalculatorFactory implements PolicyFactory {
    private AmountCalculator cache;
    private Money amount;
    private Set<DiscountCondition> conditions = new HashSet<>();

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

    @Override
    public Set<DiscountCondition> getConditions() {
        return conditions;
    }

    public void addCondition(final DiscountCondition condition) {
        this.conditions.add(condition);
    }
}
