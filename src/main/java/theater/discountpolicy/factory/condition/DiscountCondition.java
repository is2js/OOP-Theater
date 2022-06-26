package theater.discountpolicy.factory.condition;

import theater.domain.Screening;

public interface DiscountCondition {
    public boolean isSatisfiedBy(Screening screening, int audienceCount);
}
