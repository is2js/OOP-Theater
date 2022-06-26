package theater.discountpolicy.strategy;

import theater.domain.Money;

public class PercentCalculator implements Calculator {
    private Double percent;

    public PercentCalculator(final Double percent) {
        this.percent = percent;
    }

    @Override
    public Money calculateFee(final Money fee) {
        return fee.minus(fee.multi(percent));
    }
}
