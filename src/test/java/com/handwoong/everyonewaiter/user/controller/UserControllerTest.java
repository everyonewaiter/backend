package com.handwoong.everyonewaiter.user.controller;

import static com.handwoong.everyonewaiter.user.controller.snippet.UserRequestSnippet.JOIN_REQUEST;
import static com.handwoong.everyonewaiter.util.RestDocsUtils.getFilter;
import static com.handwoong.everyonewaiter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.user.controller.request.UserJoinRequest;
import com.handwoong.everyonewaiter.util.TestHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class UserControllerTest extends TestHelper {

    @Test
    void Should_Join_When_ValidRequest() {
        // given
        final UserJoinRequest request = new UserJoinRequest("handwoong", "123456", "01012345678");

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
            .filter(getFilter().document(JOIN_REQUEST))
            .when().post("/api/users")
            .then().log().all().extract();
    }
}
