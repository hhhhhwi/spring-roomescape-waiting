package roomescape.error.exception;

import roomescape.error.RoomescapeErrorMessage;

public class IllegalReservationException extends RuntimeException{

    public IllegalReservationException() {
        super(RoomescapeErrorMessage.ILLEGAL_RESERVATION_EXCEPTION);
    }
}
