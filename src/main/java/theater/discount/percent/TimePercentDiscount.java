package theater.discount.percent;

import java.time.LocalDateTime;
import theater.Screening;

public class TimePercentDiscount extends PercentDiscount {
    private LocalDateTime whenScreened;

    public TimePercentDiscount(final double percent, final LocalDateTime whenScreened) {
        super(percent);
        this.whenScreened = whenScreened;
    }

    @Override
    public boolean isSatisfiedBy(final Screening screening, final int audienceCount) {
        return screening.whenScreened == whenScreened;
    }
}
