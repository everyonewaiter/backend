package com.handwoong.everyonewaiter.medium.user;

import static com.handwoong.everyonewaiter.common.domain.PhoneNumber.PHONE_NUMBER_FORMAT_MESSAGE;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getFilter;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getSpecification;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreResponseSnippet.CUD_RESPONSE;
import static com.handwoong.everyonewaiter.medium.user.snippet.UserRequestSnippet.JOIN_REQUEST;
import static com.handwoong.everyonewaiter.medium.user.snippet.UserRequestSnippet.LOGIN_REQUEST;
import static com.handwoong.everyonewaiter.medium.user.snippet.UserResponseSnippet.JOIN_RESPONSE;
import static com.handwoong.everyonewaiter.medium.user.snippet.UserResponseSnippet.LOGIN_RESPONSE;
import static com.handwoong.everyonewaiter.user.controller.request.UserLoginRequest.LOGIN_MESSAGE;
import static com.handwoong.everyonewaiter.user.domain.Password.PASSWORD_FORMAT_MESSAGE;
import static com.handwoong.everyonewaiter.user.domain.Username.USERNAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.user.domain.Username.USERNAME_MAX_LENGTH_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.medium.TestHelper;
import com.handwoong.everyonewaiter.user.controller.request.UserJoinRequest;
import com.handwoong.everyonewaiter.user.controller.request.UserLoginRequest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql"})
class UserControllerTest extends TestHelper {

	@Test
	void Should_Verify_When_Authentication() {
		// given
		final String token = userAccessToken;

		// when
		final ExtractableResponse<Response> response = verify(token);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
	}

	@Test
	void Should_VerifyStatus401_When_FailAuthentication() {
		// given
		final String token = "";

		// when
		final ExtractableResponse<Response> response = verify(token);

		// then
		assertThat(response.statusCode()).isEqualTo(401);
	}

	private ExtractableResponse<Response> verify(final String token) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.filter(getFilter().document(AUTHORIZATION_HEADER, CUD_RESPONSE))
				.when().get("/api/users/verify")
				.then().log().all().extract();
	}

	@Test
	void Should_Join_When_ValidRequest() {
		// given
		final UserJoinRequest request = new UserJoinRequest("username", "123456", "01012345678");

		// when
		final ExtractableResponse<Response> response = join(request);

		// then
		assertThat(response.statusCode()).isEqualTo(201);
	}

	@Test
	void Should_JoinStatus400_When_DuplicateUsername() {
		// given
		final UserJoinRequest request = new UserJoinRequest("handwoong", "123456", "01012345678");

		// when
		final ExtractableResponse<Response> response = join(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo("이미 존재하는 사용자 아이디입니다.");
	}

	@ParameterizedTest(name = "사용자 아이디 {index} : [{0}]")
	@ValueSource(strings = {"", " "})
	void Should_JoinStatus400_When_UsernameBlank(final String username) {
		// given
		final UserJoinRequest request = new UserJoinRequest(username, "123456", "01012345678");

		// when
		final ExtractableResponse<Response> response = join(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(USERNAME_EMPTY_MESSAGE);
	}

	@ParameterizedTest(name = "비밀번호 {index} : [{0}]")
	@ValueSource(strings = {"", " "})
	void Should_JoinStatus400_When_PasswordBlank(final String password) {
		// given
		final UserJoinRequest request = new UserJoinRequest("username", password, "01012345678");

		// when
		final ExtractableResponse<Response> response = join(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(PASSWORD_FORMAT_MESSAGE);
	}

	@ParameterizedTest(name = "휴대폰 번호 {index} : [{0}]")
	@ValueSource(strings = {"", " "})
	void Should_JoinStatus400_When_PhoneNumberBlank(final String phoneNumber) {
		// given
		final UserJoinRequest request = new UserJoinRequest("username", "123456", phoneNumber);

		// when
		final ExtractableResponse<Response> response = join(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(PHONE_NUMBER_FORMAT_MESSAGE);
	}

	@Test
	void Should_JoinStatus400_When_UsernameLengthGreaterThan30() {
		// given
		final String username = "username".repeat(4);
		final UserJoinRequest request = new UserJoinRequest(username, "123456", "01012345678");

		// when
		final ExtractableResponse<Response> response = join(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(USERNAME_MAX_LENGTH_MESSAGE);
	}

	@Test
	void Should_JoinStatus400_When_InvalidPasswordFormat() {
		// given
		final UserJoinRequest request = new UserJoinRequest("username", "invalid", "01012345678");

		// when
		final ExtractableResponse<Response> response = join(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(PASSWORD_FORMAT_MESSAGE);
	}

	@Test
	void Should_JoinStatus400_When_InvalidPhoneNumberFormat() {
		// given
		final UserJoinRequest request = new UserJoinRequest("username", "123456", "012345678910");

		// when
		final ExtractableResponse<Response> response = join(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(PHONE_NUMBER_FORMAT_MESSAGE);
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
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(result.resultCode()).isEqualTo(ResultCode.SUCCESS);
	}

	@Test
	void Should_LoginStatus400_When_UserNotFound() {
		// given
		final UserLoginRequest request = new UserLoginRequest("username", "123456");

		// when
		final ExtractableResponse<Response> response = login(request);
		final TypeRef<ApiResponse<JwtToken>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<JwtToken> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo("아이디와 비밀번호를 확인 해주세요.");
	}

	@Test
	void Should_LoginStatus400_When_PasswordNotMatched() {
		// given
		final UserLoginRequest request = new UserLoginRequest("handwoong", "111111");

		// when
		final ExtractableResponse<Response> response = login(request);
		final TypeRef<ApiResponse<JwtToken>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<JwtToken> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo("아이디와 비밀번호를 확인 해주세요.");
	}

	@ParameterizedTest(name = "사용자 아이디 {index} : [{0}]")
	@ValueSource(strings = {"", " "})
	void Should_LoginStatus400_When_UsernameBlank(final String username) {
		// given
		final UserLoginRequest request = new UserLoginRequest(username, "123456");

		// when
		final ExtractableResponse<Response> response = login(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(LOGIN_MESSAGE);
	}

	@ParameterizedTest(name = "비밀번호 {index} : [{0}]")
	@ValueSource(strings = {"", " "})
	void Should_LoginStatus400_When_PasswordBlank(final String password) {
		// given
		final UserLoginRequest request = new UserLoginRequest("username", password);

		// when
		final ExtractableResponse<Response> response = login(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(LOGIN_MESSAGE);
	}

	@Test
	void Should_LoginStatus400_When_UsernameLengthGreaterThan30() {
		// given
		final String username = "username".repeat(4);
		final UserLoginRequest request = new UserLoginRequest(username, "123456");

		// when
		final ExtractableResponse<Response> response = login(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(LOGIN_MESSAGE);
	}

	@Test
	void Should_LoginStatus400_When_InvalidPasswordFormat() {
		// given
		final UserLoginRequest request = new UserLoginRequest("username", "invalid");

		// when
		final ExtractableResponse<Response> response = login(request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(LOGIN_MESSAGE);
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
