package theater.discount;

import theater.Money;
import theater.Screening;

public class SequenceAmountDiscount extends AmountDiscount {
    private int sequence;

    protected SequenceAmountDiscount(final Money amount, final int sequence) {
        super(amount);
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(final Screening screening, final int audienceCount) {
        return screening.sequence == sequence;
    }
}
