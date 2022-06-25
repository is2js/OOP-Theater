package theater.discount.policy.strategy;

import theater.domain.Money;

public interface Calculator {

    Money calculateFee(Money fee);
}
