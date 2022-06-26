package theater.discountpolicy.strategy;

import theater.domain.Money;

public class NosaleCalculator implements Calculator {
    public NosaleCalculator() {
    }

    @Override
    public Money calculateFee(final Money fee) {
        return fee;
    }
}
