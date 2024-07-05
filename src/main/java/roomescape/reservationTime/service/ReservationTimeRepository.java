package roomescape.reservationTime.service;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import roomescape.reservationTime.ReservationTime;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {
    List<ReservationTime> findByIdNotIn(List<Long> id);
}
