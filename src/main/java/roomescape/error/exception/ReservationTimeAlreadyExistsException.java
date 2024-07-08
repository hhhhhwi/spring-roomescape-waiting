package roomescape.error.exception;

public class ReservationTimeAlreadyExistsException extends AlreadyExistsException {
    public ReservationTimeAlreadyExistsException() {
        super("해당 예약 시간이");
    }
}
