package roomescape.reservation;

import jakarta.persistence.*;
import roomescape.member.Member;
import roomescape.reservationtime.ReservationTime;
import roomescape.theme.Theme;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    private ReservationTime reservationTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Theme theme;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;

    public Reservation(Long id, Member member, LocalDate date, ReservationTime reservationTime, Theme theme,
                       ReservationStatus reservationStatus) {
        this.id = id;
        this.member = member;
        this.date = date;
        this.reservationTime = reservationTime;
        this.theme = theme;
        this.reservationStatus = reservationStatus;
    }

    public Reservation(Member member, String date, ReservationTime reservationTime, Theme theme,
                       ReservationStatus reservationStatus) {
        this(null, member, LocalDate.parse(date), reservationTime, theme, reservationStatus);
    }

    protected Reservation() {
    }

    public boolean isBeforeThan(LocalDateTime dateTime) {
        return LocalDateTime.of(date, reservationTime.getStartAt()).isBefore(dateTime);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public Theme getTheme() {
        return theme;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }
}
