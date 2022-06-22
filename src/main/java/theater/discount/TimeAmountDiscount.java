package theater.discount;

import java.time.LocalDateTime;
import theater.Money;
import theater.Screening;

public class TimeAmountDiscount extends AmountDiscount {
    private LocalDateTime whenScreened;

    protected TimeAmountDiscount(final Money amount, final LocalDateTime whenScreened) {
        super(amount);
        this.whenScreened = whenScreened;
    }

    @Override
    public boolean isSatisfiedBy(final Screening screening, final int audienceCount) {
        return screening.whenScreened == whenScreened;
    }
}
