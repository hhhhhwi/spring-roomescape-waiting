package roomescape.reservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import roomescape.member.Member;
import roomescape.reservation.Reservation;
import roomescape.reservation.WaitingReservation;
import roomescape.reservationtime.ReservationTime;
import roomescape.theme.Theme;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDateAndReservationTimeAndTheme(LocalDate date, ReservationTime time, Theme theme);

    Optional<Reservation> findByReservationTime(ReservationTime time);

    Optional<Reservation> findByTheme(Theme theme);

    List<Reservation> findByDateAndTheme(LocalDate date, Theme theme);

    List<Reservation> findByMember(Member member);

    @Query("SELECT new roomescape.reservation.WaitingReservation(" +
        "    r, " +
        "    (SELECT COUNT(r) " +
        "     FROM Reservation r2 " +
        "     WHERE r2.theme = r.theme " +
        "       AND r2.date = r.date " +
        "       AND r2.reservationTime = r.reservationTime " +
        "       AND r2.id < r.id)) " +
        "FROM Reservation r " +
        "WHERE r.member.id = :memberId")
    List<WaitingReservation> findWithRankByMember(Long memberId);
}
