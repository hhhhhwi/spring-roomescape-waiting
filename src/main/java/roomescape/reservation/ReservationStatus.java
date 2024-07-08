package roomescape.reservation;

public enum ReservationStatus {
    RESERVATION("예약"),
    WAITING("대기 중");

    private final String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
