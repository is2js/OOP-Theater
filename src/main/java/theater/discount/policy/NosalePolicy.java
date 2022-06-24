package theater.discount.policy;

import theater.domain.Money;

public class NosalePolicy extends DiscountPolicy {
    @Override
    protected Money calculateFee(final Money fee) {
        return fee;
    }
}
