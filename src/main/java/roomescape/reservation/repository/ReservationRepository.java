package roomescape.reservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import roomescape.member.Member;
import roomescape.reservation.Reservation;
import roomescape.reservationtime.ReservationTime;
import roomescape.theme.Theme;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDateAndReservationTimeAndTheme(LocalDate date, ReservationTime time, Theme theme);

    Optional<Reservation> findByReservationTime(ReservationTime time);

    Optional<Reservation> findByTheme(Theme theme);

    List<Reservation> findByDateAndTheme(LocalDate date, Theme theme);

    List<Reservation> findByMember(Member member);
}
