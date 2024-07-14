package roomescape.reservation.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import roomescape.reservation.Reservation;
import roomescape.reservation.Waiting;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {
    Optional<Waiting> findByReservation(Reservation reservation);
}
