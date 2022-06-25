package theater.discount.policy;

import java.util.HashSet;
import java.util.Set;
import theater.discount.condition.DiscountCondition;
import theater.domain.Money;
import theater.domain.Screening;

public class DiscountPolicy {

    private final Set<DiscountCondition> conditions = new HashSet<>();
    private CalculatorFactory calculatorFactory;

    public DiscountPolicy(final CalculatorFactory calculatorFactory) {
        this.calculatorFactory = calculatorFactory;
    }

    public void addCondition(DiscountCondition discountCondition){
        this.conditions.add(discountCondition);
    }

    public void copyCondition(DiscountPolicy discountPolicy){
        discountPolicy.conditions.addAll(conditions);
    }

    public Money calculateFee(Screening screening, int count, Money fee){
        for (final DiscountCondition condition : conditions) {
            if (condition.isSatisfiedBy(screening, count)) {
                return calculatorFactory.calculateFee(fee);
            }
        }
        return fee;
    }
}
