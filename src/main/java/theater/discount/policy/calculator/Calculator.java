package theater.discount.policy.calculator;

import theater.domain.Money;

public interface Calculator {
    Money calculateFee(Money fee);
}
