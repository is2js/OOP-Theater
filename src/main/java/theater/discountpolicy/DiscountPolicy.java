package theater.discountpolicy;

import theater.discountpolicy.factory.condition.DiscountCondition;
import theater.discountpolicy.factory.PolicyFactory;
import theater.domain.Money;
import theater.domain.Screening;

public class DiscountPolicy {

    private PolicyFactory factory;

    public DiscountPolicy(final PolicyFactory factory) {
        this.factory = factory;
    }

    public Money calculateFee(Screening screening, int count, Money fee){
        for (final DiscountCondition condition : factory.getConditions()) {
            if (condition.isSatisfiedBy(screening, count)) {
                return factory.calculateFee(fee);
            }
        }
        return fee;
    }
}
