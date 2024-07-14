package roomescape.reservationtime.dto;

import roomescape.reservationtime.ReservationTime;

public class ReservationTimeResponse {

    private Long id;

    private String startAt;

    private boolean alreadyBooked;

    private ReservationTimeResponse(Long id, String startAt, boolean alreadyBooked) {
        this.id = id;
        this.startAt = startAt;
        this.alreadyBooked = alreadyBooked;
    }

    public static ReservationTimeResponse of(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(),
            reservationTime.getStartAt().toString(), true);
    }

    public static ReservationTimeResponse from(ReservationTime reservationTime, boolean alreadyBooked) {
        return new ReservationTimeResponse(reservationTime.getId(),
            reservationTime.getStartAt().toString(), alreadyBooked);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    public boolean isAlreadyBooked() {
        return alreadyBooked;
    }
}
