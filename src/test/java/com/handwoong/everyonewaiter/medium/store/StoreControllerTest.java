package com.handwoong.everyonewaiter.medium.store;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getFilter;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getSpecification;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreRequestSnippet.CREATE_REQUEST;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreResponseSnippet.CREATE_RESPONSE;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.medium.TestHelper;
import com.handwoong.everyonewaiter.store.controller.request.StoreBreakTimeRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreBusinessTimeRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateOptionRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class StoreControllerTest extends TestHelper {

    @Test
    void Should_Create_When_ValidRequest() throws Exception {
        // given
        final String token = userAccessToken;
        final StoreCreateRequest request = new StoreCreateRequest("나루", "0551234567",
            List.of(
                new StoreBreakTimeRequest(
                    LocalTime.of(15, 0, 0),
                    LocalTime.of(16, 30, 0),
                    List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
                ),
                new StoreBreakTimeRequest(
                    LocalTime.of(15, 30, 0),
                    LocalTime.of(17, 0, 0),
                    List.of(SATURDAY, SUNDAY)
                )
            ),
            List.of(
                new StoreBusinessTimeRequest(
                    LocalTime.of(11, 0, 0),
                    LocalTime.of(21, 0, 0),
                    List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
                )
            ),
            new StoreCreateOptionRequest(true, true, true)
        );

        // when
        final ExtractableResponse<Response> response = create(token, request);

        // then
        assertThat(response).extracting("statusCode").isEqualTo(201);
    }

    private ExtractableResponse<Response> create(
        final String token,
        final StoreCreateRequest request
    ) throws Exception {
        final String body = objectMapper.writeValueAsString(request);
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(body)
            .filter(getFilter().document(AUTHORIZATION_HEADER, CREATE_REQUEST, CREATE_RESPONSE))
            .when().post("/api/stores")
            .then().log().all().extract();
    }
}
