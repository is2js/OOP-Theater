package theater.domain;

import java.time.LocalDateTime;

public class Screening {
    public final int sequence;
    public final LocalDateTime whenScreened;
    private int seat;

    public Screening(final int sequence, final LocalDateTime whenScreened, final int seat) {
        this.sequence = sequence;
        this.whenScreened = whenScreened;
        this.seat = seat;
    }

    //필드변화 trigger
    public boolean hasSeat(int count){
        return seat >= count;
    }

    //필드변화 action
    public void reserveSeat(int count){
        if (hasSeat(count)) {
            seat -= count;
            return;
        }
        throw new RuntimeException("no seat");
    }
}
