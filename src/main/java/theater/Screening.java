package theater;

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
    boolean hasSeat(int count){
        return seat >= count;
    }

    //필드변화 action
    void reserveSeat(int count){
        if (hasSeat(count)) {
            seat -= count;
        }
        throw new RuntimeException("no seat");
    }
}
