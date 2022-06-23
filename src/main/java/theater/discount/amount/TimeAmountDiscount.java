package theater.discount.amount;

import java.time.LocalDateTime;
import theater.domain.Money;
import theater.domain.Screening;

public class TimeAmountDiscount extends AmountDiscount {
    private LocalDateTime whenScreened;

    public TimeAmountDiscount(final Money amount, final LocalDateTime whenScreened) {
        super(amount);
        this.whenScreened = whenScreened;
    }

    @Override
    public boolean isSatisfiedBy(final Screening screening, final int audienceCount) {
        return screening.whenScreened == whenScreened;
    }
}
