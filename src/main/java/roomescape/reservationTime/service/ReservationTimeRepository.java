package roomescape.reservationTime.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import roomescape.reservationTime.ReservationTime;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {
    Optional<ReservationTime> findByIdNotIn(List<Long> id);
}
