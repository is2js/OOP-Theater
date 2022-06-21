package theater;

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
}
