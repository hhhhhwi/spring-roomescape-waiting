package roomescape.reservation.dto;

import roomescape.reservation.Reservation;

public class MyReservationResponse {

    private Long reservationId;

    private String theme;

    private String date;

    private String time;

    private String status;

    public MyReservationResponse(Long reservationId, String theme, String date, String time,
        String status) {
        this.reservationId = reservationId;
        this.theme = theme;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public MyReservationResponse(Reservation reservation) {
        this(reservation.getId(),
            reservation.getTheme().getName(),
            reservation.getDate().toString(),
            reservation.getReservationTime().getStartAt().toString(),
            reservation.getReservationStatus().getDescription());
    }

    public Long getReservationId() {
        return reservationId;
    }

    public String getTheme() {
        return theme;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
