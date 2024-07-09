package roomescape.error.exception;

import roomescape.error.RoomescapeErrorMessage;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message + RoomescapeErrorMessage.ALREADY_EXISTS_EXCEPTION);
    }
}
