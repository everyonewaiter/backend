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
import static com.handwoong.everyonewaiter.store.domain.LandlineNumber.LANDLINE_NUMBER_FORMAT_MESSAGE;
import static com.handwoong.everyonewaiter.store.domain.StoreName.STORE_NAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.store.domain.StoreName.STORE_NAME_MAX_LENGTH_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.medium.TestHelper;
import com.handwoong.everyonewaiter.store.controller.request.StoreBreakTimeRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreBusinessTimeRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateOptionRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateRequest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void Should_CreateStatus400_When_StoreNameBlank(final String storeName) throws Exception {
        // given
        final String token = userAccessToken;
        final StoreCreateRequest request = new StoreCreateRequest(storeName, "0551234567",
            List.of(),
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
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo(STORE_NAME_EMPTY_MESSAGE);
    }

    @Test
    void Should_CreateStatus400_When_StoreNameLengthGreaterThan50() throws Exception {
        // given
        final String token = userAccessToken;
        final StoreCreateRequest request = new StoreCreateRequest("나루 레스토랑".repeat(8), "0551234567",
            List.of(),
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
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo(STORE_NAME_MAX_LENGTH_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void Should_CreateStatus400_When_LandlineNumberBlank(final String landlineNumber) throws Exception {
        // given
        final String token = userAccessToken;
        final StoreCreateRequest request = new StoreCreateRequest("나루", landlineNumber,
            List.of(),
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
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo(LANDLINE_NUMBER_FORMAT_MESSAGE);
    }

    @Test
    void Should_CreateStatus400_When_InvalidLandlineNumberFormat() throws Exception {
        // given
        final String token = userAccessToken;
        final StoreCreateRequest request = new StoreCreateRequest("나루", "055-123-4567",
            List.of(),
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
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo(LANDLINE_NUMBER_FORMAT_MESSAGE);
    }

    @Test
    void Should_CreateStatus400_When_BreakTimesDayOfWeekEmpty() throws Exception {
        // given
        final String token = userAccessToken;
        final StoreCreateRequest request = new StoreCreateRequest("나루", "0551234567",
            List.of(
                new StoreBreakTimeRequest(
                    LocalTime.of(15, 0, 0),
                    LocalTime.of(16, 30, 0),
                    List.of()
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
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo("브레이크 타임의 요일을 하나 이상 등록해주세요.");
    }

    @Test
    void Should_CreateStatus400_When_BusinessTimesDayOfWeekEmpty() throws Exception {
        // given
        final String token = userAccessToken;
        final StoreCreateRequest request = new StoreCreateRequest("나루", "0551234567",
            List.of(),
            List.of(
                new StoreBusinessTimeRequest(
                    LocalTime.of(11, 0, 0),
                    LocalTime.of(21, 0, 0),
                    List.of()
                )
            ),
            new StoreCreateOptionRequest(true, true, true)
        );

        // when
        final ExtractableResponse<Response> response = create(token, request);
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo("영업 시간의 요일을 하나 이상 등록해주세요.");
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
