package theater.discount.policy.factory;

import theater.discount.policy.strategy.Calculator;

public interface CalculatorFactory {

    Calculator getCalculator();
}
