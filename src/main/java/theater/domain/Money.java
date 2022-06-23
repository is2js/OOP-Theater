package theater.domain;

public class Money {

    private final Double amount;

    public Money(final Double amount) {
        this.amount = amount;
    }

    public static Money of(final Double amount) {
        return new Money(amount);
    }

    public Money minus(final Money amount) {
        if (this.amount > amount.amount) {
            return new Money( this.amount - amount.amount);
        }
        return new Money(0.0);
    }

    public Money plus(final Money amount){
        return new Money(this.amount + amount.amount);
    }

    public Money multi(final double times) {
        return new Money(this.amount * times);
    }

    public boolean greaterThan(final Money amount) {
        return this.amount >= amount.amount;
    }
}
