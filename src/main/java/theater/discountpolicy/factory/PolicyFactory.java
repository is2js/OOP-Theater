package theater.discountpolicy.factory;

import java.util.Set;
import theater.discountpolicy.factory.condition.DiscountCondition;
import theater.discountpolicy.strategy.Calculator;

public interface PolicyFactory extends Calculator {
    Set<DiscountCondition> getConditions();
}
