package roomescape.reservationtime.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import roomescape.error.exception.ReservationTimeNotExistsException;
import roomescape.error.exception.ReservationTimeReferenceException;
import roomescape.error.exception.ThemeNotExistsException;
import roomescape.reservation.repository.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.dto.ReservationTimeResponse;
import roomescape.reservationtime.repository.ReservationTimeRepository;
import roomescape.theme.Theme;
import roomescape.theme.repository.ThemeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository,
                                    ReservationRepository reservationRepository,
                                    ThemeRepository themeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public ReservationTimeResponse saveReservationTime(ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.getStartAt());
        return new ReservationTimeResponse(reservationTimeRepository.save(reservationTime));
    }

    public List<ReservationTimeResponse> findReservationTimes() {
        return reservationTimeRepository.findAll().stream()
            .map(ReservationTimeResponse::new)
            .collect(Collectors.toList());
    }

    public void deleteReservationTime(Long id) {
        ReservationTime reservationTime = reservationTimeRepository.findById(id)
            .orElseThrow(ReservationTimeNotExistsException::new);

        if (reservationRepository.findByReservationTime(reservationTime).isPresent()) {
            throw new ReservationTimeReferenceException();
        }

        reservationTimeRepository.deleteById(id);
    }

    public List<ReservationTimeResponse> findAvailableReservationTimes(String date, Long themeId) {
        Theme theme = themeRepository.findById(themeId)
            .orElseThrow(ThemeNotExistsException::new);
        List<Long> ids = reservationRepository.findByDateAndTheme(LocalDate.parse(date), theme)
            .stream().map(reservation -> reservation.getReservationTime().getId())
            .toList();

        if(ids.isEmpty()) {
            return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::new)
                .collect(Collectors.toList());
        }

        return reservationTimeRepository.findByIdNotIn(ids).stream()
            .map(ReservationTimeResponse::new)
            .collect(Collectors.toList());
    }
}
