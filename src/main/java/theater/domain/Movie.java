package theater.domain;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import theater.discount.DiscountCondition;
import theater.discount.DiscountPolicy;

public class Movie<T extends DiscountPolicy & DiscountCondition> {
    private final String title;
    private final Duration duration;
    private final Money fee;
    private final Set<T> discountConditions = new HashSet<>();

    public Movie(final String title,
                 final Duration duration,
                 final Money fee,
                 final T... discountCondition) {
        this.title = title;
        this.duration = duration;
        this.fee = fee;
        this.discountConditions.addAll(Arrays.asList(discountCondition));
    }

    public Money calculateFee(Screening screening, int audienceCount){
        for (final T discountCondition : discountConditions) {
            if (discountCondition.isSatisfiedBy(screening, audienceCount)) {
                return discountCondition.calculateFee(fee)
                    .multi(audienceCount);
            }
        }
        return fee.multi(audienceCount);
    }
}
