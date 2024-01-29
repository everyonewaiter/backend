package com.handwoong.everyonewaiter.user.controller;

import static com.handwoong.everyonewaiter.user.controller.snippet.UserRequestSnippet.JOIN_REQUEST;
import static com.handwoong.everyonewaiter.user.controller.snippet.UserRequestSnippet.LOGIN_REQUEST;
import static com.handwoong.everyonewaiter.user.controller.snippet.UserResponseSnippet.JOIN_RESPONSE;
import static com.handwoong.everyonewaiter.user.controller.snippet.UserResponseSnippet.LOGIN_RESPONSE;
import static com.handwoong.everyonewaiter.util.RestDocsUtils.getFilter;
import static com.handwoong.everyonewaiter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.user.controller.request.UserJoinRequest;
import com.handwoong.everyonewaiter.user.controller.request.UserLoginRequest;
import com.handwoong.everyonewaiter.util.TestHelper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class UserControllerTest extends TestHelper {

    @Test
    void Should_Join_When_ValidRequest() {
        // given
        final UserJoinRequest request = new UserJoinRequest("username", "123456", "01012345678");

        // when
        final ExtractableResponse<Response> response = join(request);

        // then
        assertThat(response).extracting("statusCode").isEqualTo(201);
    }

    private ExtractableResponse<Response> join(final UserJoinRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(JOIN_REQUEST, JOIN_RESPONSE))
            .when().post("/api/users")
            .then().log().all().extract();
    }

    @Test
    void Should_Login_When_ValidRequest() {
        // given
        final UserLoginRequest request = new UserLoginRequest("handwoong", "123456");

        // when
        final ExtractableResponse<Response> response = login(request);
        final TypeRef<ApiResponse<JwtToken>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<JwtToken> result = response.body().as(typeRef);

        // then
        assertThat(response).extracting("statusCode").isEqualTo(200);
        assertThat(result).extracting("resultCode").isEqualTo(ResultCode.SUCCESS);
    }

    private ExtractableResponse<Response> login(final UserLoginRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(LOGIN_REQUEST, LOGIN_RESPONSE))
            .when().post("/api/users/login")
            .then().log().all().extract();
    }
}
