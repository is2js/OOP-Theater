package theater.discount.policy;

import java.util.HashSet;
import java.util.Set;
import theater.discount.condition.DiscountCondition;
import theater.domain.Money;
import theater.domain.Screening;

public abstract class DiscountPolicy {

    private final Set<DiscountCondition> conditions = new HashSet<>();

    public void addCondition(DiscountCondition discountCondition){
        this.conditions.add(discountCondition);
    }

    public void copyCondition(DiscountPolicy discountPolicy){
        discountPolicy.conditions.addAll(conditions);
    }

    public Money calculateFee(Screening screening, int count, Money fee){
        for (final DiscountCondition condition : conditions) {
            if (condition.isSatisfiedBy(screening, count)) {
                return calculateFee(fee);
            }
        }
        return fee;
    }

    protected abstract Money calculateFee(final Money fee);
}
