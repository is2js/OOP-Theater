package theater;

import java.time.Duration;
import java.time.LocalDateTime;
import theater.discount.condition.PeriodCondition;
import theater.discount.condition.SequenceCondition;
import theater.discount.policy.DiscountPolicy;
import theater.discount.policy.strategy.AmountCalculatorFactory;
import theater.domain.Customer;
import theater.domain.Money;
import theater.domain.Movie;
import theater.domain.Screening;
import theater.domain.theater.Theater;
import theater.domain.theater.TicketOffice;
import theater.domain.theater.TicketSeller;

public class Main {
    public static void main(final String[] args) {
        final Theater theater = new Theater(Money.of(100.0));

        final AmountCalculatorFactory amountCalculatorFactory = new AmountCalculatorFactory((Money.of(1000.0)));
        amountCalculatorFactory.addCondition(new SequenceCondition(1));
        amountCalculatorFactory.addCondition(new PeriodCondition(LocalDateTime.of(2019, 7, 7, 1, 00, 00)));

        final DiscountPolicy discountPolicy = new DiscountPolicy(amountCalculatorFactory);

        Movie movie = new Movie(
            "spiderman",
            Duration.ofMinutes(120L),
            Money.of(5000.0),
            discountPolicy
        );

        theater.addMovie(movie);

        for (int day = 7; day < 32; day++) {
            for (int hour = 10, seq = 1; hour < 24; hour +=3, seq++) {
                theater.addScreening(
                    movie, //fk
                    new Screening(
                        seq,
                        LocalDateTime.of(2019, 7, day, hour, 00, 00),
                        100
                    )
                );
            }
        }

        final TicketOffice ticketOffice = new TicketOffice(Money.of(0.0));
        theater.contractTicketOffice(ticketOffice, 10.0);

        final TicketSeller ticketSeller = new TicketSeller();
        ticketSeller.setTicketOffice(ticketOffice);

        final Customer customer = new Customer(Money.of(20000.0));

        for (Screening screening: theater.getScreening(movie)) {
            customer.reserve(ticketSeller, theater, movie, screening, 2);
            final boolean isOk = theater.enter(customer, 2);
            System.out.println("isOk = " + isOk);
            break;
        }
    }
}
