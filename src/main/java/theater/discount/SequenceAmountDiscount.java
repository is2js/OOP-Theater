package theater.discount;

import theater.Money;

public class SequenceAmountDiscount extends SequenceDiscount implements DiscountPolicy.AMOUNT {
    private Money amount;

    protected SequenceAmountDiscount(final int sequence, final Money amount) {
        super(sequence);
        this.amount = amount;
    }

    @Override
    public Money calculateFee(final Money fee) {
        return fee.minus(amount);
    }
}
