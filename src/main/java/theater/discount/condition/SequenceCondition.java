package theater.discount.condition;

import theater.domain.Screening;

public class SequenceCondition implements DiscountCondition {
    private int sequence;

    public SequenceCondition(final int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(final Screening screening, final int audienceCount) {
        return screening.sequence == sequence;
    }
}