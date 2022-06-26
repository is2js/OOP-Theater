package theater.discountpolicy.factory.condition;

import java.time.LocalDateTime;
import theater.domain.Screening;

public class PeriodCondition implements DiscountCondition {
    private LocalDateTime whenScreened;

    public PeriodCondition(final LocalDateTime whenScreened) {
        this.whenScreened = whenScreened;
    }

    @Override
    public boolean isSatisfiedBy(final Screening screening, final int audienceCount) {
        return screening.whenScreened.equals(whenScreened);
    }
}
