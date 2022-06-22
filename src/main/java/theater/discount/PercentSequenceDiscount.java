package theater.discount;

import theater.Money;

public class PercentSequenceDiscount extends SequenceDiscount implements DiscountPolicy.PERCENT {
    private double percent;

    protected PercentSequenceDiscount(final int sequence, final double percent) {
        super(sequence);
        this.percent = percent;
    }

    @Override
    public Money calculateFee(final Money fee) {
        return fee.minus(fee.multi(percent));
    }
}
