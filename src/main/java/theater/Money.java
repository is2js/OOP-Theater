package theater;

public class Money {

    private final Double amount;

    public Money(final Double amount) {
        this.amount = amount;
    }

    public static Money of(final Double amount) {
        return new Money(amount);
    }
}
