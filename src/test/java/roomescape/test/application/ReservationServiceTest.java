package roomescape.test.application;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.member.Member;
import roomescape.member.MemberRole;
import roomescape.member.repository.MemberRepository;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationStatus;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.service.ReservationService;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.repository.ReservationTimeRepository;
import roomescape.theme.Theme;
import roomescape.theme.repository.ThemeRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationTimeRepository reservationTimeRepository;

    @Mock
    private ThemeRepository themeRepository;

    @Mock
    private MemberRepository memberRepository;
    private ReservationService reservationService;

    private final Long memberId = 1L;
    private final String memberName = "member";
    private final Long timeId = 1L;
    private final String time = "10:00";
    private final Long themeId = 1L;
    private final String themeName = "theme";
    private final Long reservationId = 1L;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository, themeRepository,
                memberRepository);
    }

    @Test
    @DisplayName("예약을 등록한다")
    void saveReservation() {
        Member member = new Member(memberId, "email", "password", memberName, MemberRole.MEMBER);
        ReservationTime reservationTime = new ReservationTime(timeId, LocalTime.parse(time));
        Theme theme = new Theme(themeId, themeName, "description", "thumbnail");
        String reservationDate = LocalDate.now().plusDays(1).toString();
        Reservation reservation = new Reservation(member, reservationDate, reservationTime, theme, ReservationStatus.RESERVATION);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
        given(reservationTimeRepository.findById(timeId)).willReturn(Optional.of(reservationTime));
        given(themeRepository.findById(themeId)).willReturn(Optional.of(theme));
        given(reservationRepository.save(reservation))
                .willReturn(new Reservation(reservationId, member,  LocalDate.now().plusDays(1), reservationTime, theme, ReservationStatus.RESERVATION));

        ReservationResponse response = reservationService.saveReservation(memberId, new ReservationRequest(reservationDate, timeId, themeId));

        assertThat(response.getTime()).isEqualTo(time);
        assertThat(response.getName()).isEqualTo(memberName);
        assertThat(response.getThemeName()).isEqualTo(themeName);
    }
}
