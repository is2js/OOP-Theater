package theater.discount.policy.factory;

import theater.domain.Money;

public interface CalculatorFactory {

    Money calculateFee(Money fee);
}
