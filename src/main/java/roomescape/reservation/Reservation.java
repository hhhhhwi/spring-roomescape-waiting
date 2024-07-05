package roomescape.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import roomescape.member.Member;
import roomescape.reservationtime.ReservationTime;
import roomescape.theme.Theme;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    private ReservationTime reservationTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Theme theme;

    public Reservation(Long id, Member member, LocalDate date, ReservationTime reservationTime, Theme theme) {
        this.id = id;
        this.member = member;
        this.date = date;
        this.reservationTime = reservationTime;
        this.theme = theme;
    }

    public Reservation(Member member, String date, ReservationTime reservationTime, Theme theme) {
        this(null, member, LocalDate.parse(date), reservationTime, theme);
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

}
