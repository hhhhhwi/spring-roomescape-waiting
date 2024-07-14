package roomescape.reservation.dto;

import roomescape.reservation.Reservation;

public class MyReservationResponse {

    private Long reservationId;

    private String theme;

    private String date;

    private String time;

    private String statusCode;

    private String statusText;

    private MyReservationResponse(Long reservationId, String theme, String date, String time, String statusCode, String statusText) {
        this.reservationId = reservationId;
        this.theme = theme;
        this.date = date;
        this.time = time;
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public static MyReservationResponse from(Reservation reservation) {
        return new MyReservationResponse(reservation.getId(),
            reservation.getTheme().getName(),
            reservation.getDate().toString(),
            reservation.getReservationTime().getStartAt().toString(),
            reservation.getReservationStatus().name(),
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

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }
}
