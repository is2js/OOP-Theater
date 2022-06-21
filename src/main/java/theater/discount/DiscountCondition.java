package theater.discount;

import theater.Money;

public interface DiscountCondition {
    public boolean isStatisfiedBy(Screening screening, int audienceCount);
    public Money calculateFee(Money fee);
}
