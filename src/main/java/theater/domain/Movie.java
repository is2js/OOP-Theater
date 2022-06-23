package theater.domain;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import theater.discount.DiscountCondition;
import theater.discount.DiscountPolicy;

public class Movie<T extends DiscountPolicy & DiscountCondition> {
    private final String title;
    private final Duration duration;
    private final Money fee;
    private final DiscountPolicy policy;

    public Movie(final String title,
                 final Duration duration,
                 final Money fee,
                 final DiscountPolicy policy) {
        this.title = title;
        this.duration = duration;
        this.fee = fee;
        this.policy = policy;
    }

    public Money calculateFee(Screening screening, int audienceCount){
        return policy.calculateFee(screening, audienceCount, fee);
    }
}
