package theater.discount;

import theater.Screening;

public abstract class SequenceDiscount implements DiscountCondition {
    private final int sequence;

    protected SequenceDiscount(final int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(final Screening screening, final int audienceCount) {
        //발동조건
        return screening.sequence == sequence;
    }
}
