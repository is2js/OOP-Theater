package theater.discount;

import theater.domain.Money;

public class NosalePolicy extends DiscountPolicy {
    @Override
    protected Money calculateFee(final Money fee) {
        return fee;
    }
}
