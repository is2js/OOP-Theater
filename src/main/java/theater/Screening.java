package theater;

import java.time.LocalDateTime;

public class Screening {
    public final int sequence;
    public final LocalDateTime whenScreened;

    public Screening(final int sequence, final LocalDateTime whenScreened) {
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }
}
