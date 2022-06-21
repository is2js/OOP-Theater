package theater.discount;

import theater.Money;
import theater.Screening;

public interface DiscountCondition {
    public boolean isSatisfiedBy(Screening screening, int audienceCount);
    public Money calculateFee(Money fee);
}
