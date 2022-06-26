package theater.discountpolicy.factory;

import java.util.Set;
import theater.discountpolicy.factory.condition.DiscountCondition;
import theater.discountpolicy.strategy.Calculator;
import theater.domain.Money;
import theater.domain.Screening;

public interface PolicyFactory extends Calculator {

    Set<DiscountCondition> getConditions();

    default Money calculateFee(Screening screening, int count, Money fee){
        for (final DiscountCondition condition : getConditions()) {
            if (condition.isSatisfiedBy(screening, count)) {
                return calculateFee(fee);
            }
        }
        return fee;
    }
}
