package theater.discount.policy.strategy;

import java.util.Set;
import theater.discount.condition.DiscountCondition;

public interface PolicyFactory extends Calculator {
    Set<DiscountCondition> getConditions();
}
