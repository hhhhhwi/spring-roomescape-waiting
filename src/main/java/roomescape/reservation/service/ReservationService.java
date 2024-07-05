package roomescape.reservation.service;

import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.stereotype.Service;
import roomescape.error.exception.MemberNotExistsException;
import roomescape.error.exception.PastDateTimeException;
import roomescape.error.exception.ReservationTimeNotExistsException;
import roomescape.error.exception.ThemeNotExistsException;
import roomescape.member.Member;
import roomescape.member.repository.MemberRepository;
import roomescape.reservation.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.repository.ReservationTimeRepository;
import roomescape.theme.Theme;
import roomescape.theme.repository.ThemeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final ThemeRepository themeRepository;
    private final MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository,
        ReservationTimeRepository reservationTimeRepository, ThemeRepository themeRepository,
        MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.themeRepository = themeRepository;
        this.memberRepository = memberRepository;
    }

    public List<ReservationResponse> findReservations() {
        return reservationRepository.findAll().stream()
            .map(reservation -> {
                String memberName = memberRepository.findById(reservation.getId())
                    .orElseThrow(MemberNotExistsException::new)
                    .getName();

                return new ReservationResponse(reservation, memberName);
            })
            .collect(Collectors.toList());
    }

    public ReservationResponse saveReservation(Long memberId, ReservationRequest request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotExistsException::new);
        ReservationTime reservationTime = reservationTimeRepository.findById(request.getTimeId())
            .orElseThrow(ReservationTimeNotExistsException::new);
        Theme theme = themeRepository.findById(request.getThemeId())
            .orElseThrow(ThemeNotExistsException::new);

        Reservation reservation = new Reservation(member, request.getDate(), reservationTime, theme);

        if (reservation.isBeforeThanNow()) {
            throw new PastDateTimeException();
        }

        if (reservationRepository.findByDateAndReservationTimeAndTheme(reservation.getDate(),
            reservationTime, theme).isPresent()) {
            throw new DuplicateRequestException("해당 시간 예약이");
        }

        return new ReservationResponse(reservationRepository.save(reservation), member.getName());
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }
}
