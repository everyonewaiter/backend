package com.handwoong.everyonewaiter.medium.waiting;

import static com.handwoong.everyonewaiter.common.domain.PhoneNumber.PHONE_NUMBER_FORMAT_MESSAGE;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getFilter;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getSpecification;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreResponseSnippet.CUD_RESPONSE;
import static com.handwoong.everyonewaiter.medium.waiting.snippet.WaitingRequestSnippet.CANCEL_REQUEST;
import static com.handwoong.everyonewaiter.medium.waiting.snippet.WaitingRequestSnippet.REGISTER_REQUEST;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingAdult.MAX_ADULT_MESSAGE;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingAdult.MIN_ADULT_MESSAGE;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingChildren.MAX_CHILDREN_MESSAGE;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingChildren.MIN_CHILDREN_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.medium.TestHelper;
import com.handwoong.everyonewaiter.waiting.controller.request.WaitingCancelRequest;
import com.handwoong.everyonewaiter.waiting.controller.request.WaitingRegisterRequest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql", "classpath:sql/store.sql", "classpath:sql/waiting.sql"})
class WaitingControllerTest extends TestHelper {

    @Test
    void Should_Register_When_ValidRequest() {
        // given
        final String token = userAccessToken;
        final WaitingRegisterRequest request = new WaitingRegisterRequest(2L, 4, 2, "01011112222");

        // when
        final ExtractableResponse<Response> response = register(token, request);
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(result.resultCode()).isEqualTo(ResultCode.SUCCESS);
    }

    @Test
    void Should_RegisterStatus400_When_ExistsPhoneNumber() {
        // given
        final String token = userAccessToken;
        final WaitingRegisterRequest request = new WaitingRegisterRequest(2L, 4, 2, "01012345678");

        // when
        final ExtractableResponse<Response> response = register(token, request);
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo("이미 웨이팅에 등록되어 있는 휴대폰 번호입니다.");
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void Should_RegisterStatus400_When_AdultLessThan1() {
        // given
        final String token = userAccessToken;
        final WaitingRegisterRequest request = new WaitingRegisterRequest(2L, 0, 2, "01011112222");

        // when
        final ExtractableResponse<Response> response = register(token, request);
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo(MIN_ADULT_MESSAGE);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void Should_RegisterStatus400_When_AdultGreaterThan20() {
        final String token = userAccessToken;
        final WaitingRegisterRequest request = new WaitingRegisterRequest(2L, 21, 2, "01011112222");

        // when
        final ExtractableResponse<Response> response = register(token, request);
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo(MAX_ADULT_MESSAGE);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void Should_RegisterStatus400_When_ChildrenLessThanZero() {
        // given
        final String token = userAccessToken;
        final WaitingRegisterRequest request = new WaitingRegisterRequest(2L, 4, -1, "01011112222");

        // when
        final ExtractableResponse<Response> response = register(token, request);
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo(MIN_CHILDREN_MESSAGE);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void Should_RegisterStatus400_When_ChildrenGreaterThan20() {
        final String token = userAccessToken;
        final WaitingRegisterRequest request = new WaitingRegisterRequest(2L, 4, 21, "01011112222");

        // when
        final ExtractableResponse<Response> response = register(token, request);
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo(MAX_CHILDREN_MESSAGE);
    }

    @Test
    void Should_RegisterStatus400_When_InvalidPhoneNumberFormat() {
        final String token = userAccessToken;
        final WaitingRegisterRequest request = new WaitingRegisterRequest(2L, 4, 0, "012345678910");

        // when
        final ExtractableResponse<Response> response = register(token, request);
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
        assertThat(result.message()).isEqualTo(PHONE_NUMBER_FORMAT_MESSAGE);
    }

    private ExtractableResponse<Response> register(final String token, final WaitingRegisterRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(AUTHORIZATION_HEADER, REGISTER_REQUEST, CUD_RESPONSE))
            .when().post("/api/waiting")
            .then().log().all().extract();
    }

    @Test
    void Should_Cancel_When_ValidRequest() {
        // given
        final WaitingCancelRequest request =
            new WaitingCancelRequest(2L, UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

        // when
        final ExtractableResponse<Response> response = cancel(request);
        final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<Void> result = response.body().as(typeRef);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.resultCode()).isEqualTo(ResultCode.SUCCESS);
    }

    private ExtractableResponse<Response> cancel(final WaitingCancelRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(CANCEL_REQUEST, CUD_RESPONSE))
            .when().put("/api/waiting/cancel")
            .then().log().all().extract();
    }
}
