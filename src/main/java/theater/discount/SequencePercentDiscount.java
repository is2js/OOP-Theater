package theater.discount;

import theater.Screening;

public class SequencePercentDiscount extends PercentDiscount {
    private final int sequence;

    protected SequencePercentDiscount(final double percent, final int sequence) {
        super(percent);
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(final Screening screening, final int audienceCount) {
        return screening.sequence == sequence;
    }
}
