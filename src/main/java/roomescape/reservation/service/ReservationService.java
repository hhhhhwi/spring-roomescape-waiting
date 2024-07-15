package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import roomescape.error.exception.*;
import roomescape.member.Member;
import roomescape.member.repository.MemberRepository;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationStatus;
import roomescape.reservation.Waiting;
import roomescape.reservation.dto.MyReservationResponse;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.repository.WaitingRepository;
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
    private final WaitingRepository waitingRepository;

    public ReservationService(ReservationRepository reservationRepository,
        ReservationTimeRepository reservationTimeRepository, ThemeRepository themeRepository,
        MemberRepository memberRepository, WaitingRepository waitingRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.themeRepository = themeRepository;
        this.memberRepository = memberRepository;
        this.waitingRepository = waitingRepository;
    }

    public List<ReservationResponse> findReservations() {
        return reservationRepository.findAll().stream()
            .map(ReservationResponse::of)
            .collect(Collectors.toList());
    }

    public ReservationResponse saveReservation(Long memberId, ReservationRequest request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotExistsException::new);
        ReservationTime reservationTime = reservationTimeRepository.findById(request.getTimeId())
            .orElseThrow(ReservationTimeNotExistsException::new);
        Theme theme = themeRepository.findById(request.getThemeId())
            .orElseThrow(ThemeNotExistsException::new);
        Reservation reservation = new Reservation(member, request.getDate(), reservationTime, theme,
            ReservationStatus.RESERVATION);

        if (reservation.isBeforeThan(LocalDateTime.now())) {
            throw new PastDateTimeException();
        }

        if (!reservationRepository.findByDateAndReservationTimeAndTheme(reservation.getDate(),
            reservationTime, theme).isEmpty()) {
            throw new ReservationAlreadyExistsException();
        }

        return ReservationResponse.of(reservationRepository.save(reservation));
    }

    public ReservationResponse saveWaitingReservation(Long memberId, ReservationRequest request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotExistsException::new);
        ReservationTime reservationTime = reservationTimeRepository.findById(request.getTimeId())
            .orElseThrow(ReservationTimeNotExistsException::new);
        Theme theme = themeRepository.findById(request.getThemeId())
            .orElseThrow(ThemeNotExistsException::new);
        Reservation reservation = new Reservation(member, request.getDate(), reservationTime, theme,
                ReservationStatus.WAITING);

        if (reservation.isBeforeThan(LocalDateTime.now())) {
            throw new PastDateTimeException();
        }

        List<Reservation> reservations = reservationRepository.findByDateAndReservationTimeAndTheme(
                reservation.getDate(), reservationTime, theme);

        if (reservations.size() < 1) {
            throw new IllegalReservationException();
        }

        if (reservations.stream().filter(x -> x.hasSameMember(member)).count() > 0) {
            throw new ReservationAlreadyExistsException();
        }

        reservationRepository.save(reservation);
        waitingRepository.save(new Waiting(reservation, reservations.size()));

        return ReservationResponse.of(reservation);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }

    public List<MyReservationResponse> findReservationsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotExistsException::new);

        return reservationRepository.findByMember(member).stream()
            .map(reservation -> {
                if (reservation.isWaiting()) {
                    return MyReservationResponse.from(
                        waitingRepository.findByReservation(reservation)
                            .orElseThrow(IllegalReservationException::new));
                }

                return MyReservationResponse.from(reservation);
            })
            .collect(Collectors.toList());
    }
}
