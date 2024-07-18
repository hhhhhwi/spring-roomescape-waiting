package roomescape.step;

import static roomescape.login.LoginMember.COOKIE_NAME_FOR_LOGIN;
import static roomescape.step.LoginStep.회원_토큰_생성;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import roomescape.reservation.dto.ReservationRequest;

public class ReservationStep {

    public static ExtractableResponse<Response> 예약_등록(ReservationRequest request) {
        return RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .cookie(COOKIE_NAME_FOR_LOGIN, 회원_토큰_생성())
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .extract();
    }
}
