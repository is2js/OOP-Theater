package theater.discount.amount;

import theater.domain.Money;
import theater.domain.Screening;

public class SequenceAmountDiscount extends AmountDiscount {
    private int sequence;

    public SequenceAmountDiscount(final Money amount, final int sequence) {
        super(amount);
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(final Screening screening, final int audienceCount) {
        return screening.sequence == sequence;
    }
}
