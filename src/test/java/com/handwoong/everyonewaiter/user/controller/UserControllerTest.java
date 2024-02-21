package com.handwoong.everyonewaiter.user.controller;

import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.user.controller.request.UserJoinRequest;
import com.handwoong.everyonewaiter.user.controller.request.UserLoginRequest;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.exception.AlreadyExistsUsernameException;
import com.handwoong.everyonewaiter.util.TestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

class UserControllerTest {

	@Test
	void Should_Join_When_ValidRequest() {
		// given
		final TestContainer testContainer = new TestContainer();
		final UserJoinRequest request = new UserJoinRequest("handwoong", "123456", "01012345678");

		// when
		final ResponseEntity<ApiResponse<Void>> response = testContainer.userController.join(request);
		final ApiResponse<Void> result = response.getBody();

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(201);
		assertThat(result).extracting("resultCode").isEqualTo(ResultCode.SUCCESS);
	}

	@Test
	void Should_ThrowException_When_JoinDuplicateUsername() {
		// given
		final TestContainer testContainer = new TestContainer();
		final User user = aUser().build();
		testContainer.userRepository.save(user);

		final UserJoinRequest request = new UserJoinRequest("handwoong", "123456", "01012345678");

		// expect
		assertThatThrownBy(() -> testContainer.userController.join(request))
				.isInstanceOf(AlreadyExistsUsernameException.class)
				.hasMessage("이미 존재하는 사용자 아이디입니다.");
	}

	@Test
	void Should_Login_When_ValidRequest() {
		// given
		final TestContainer testContainer = new TestContainer();
		final User user = aUser().build();
		testContainer.userRepository.save(user);

		final UserLoginRequest request = new UserLoginRequest("handwoong", "password");

		// when
		final ResponseEntity<ApiResponse<JwtToken>> response = testContainer.userController.login(request);
		final ApiResponse<JwtToken> result = response.getBody();

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(result).extracting("resultCode").isEqualTo(ResultCode.SUCCESS);
	}

	@Test
	void Should_ThrowException_When_LoginInvalidUsername() {
		// given
		final TestContainer testContainer = new TestContainer();
		final User user = aUser().build();
		testContainer.userRepository.save(user);

		final UserLoginRequest request = new UserLoginRequest("invalidUsername", "password");

		// expect
		assertThatThrownBy(() -> testContainer.userController.login(request))
				.isInstanceOf(BadCredentialsException.class)
				.hasMessage("자격 증명에 실패하였습니다.");
	}

	@Test
	void Should_ThrowException_When_LoginInvalidPassword() {
		// given
		final TestContainer testContainer = new TestContainer();
		final User user = aUser().build();
		testContainer.userRepository.save(user);

		final UserLoginRequest request = new UserLoginRequest("handwoong", "invalidPassword");

		// expect
		assertThatThrownBy(() -> testContainer.userController.login(request))
				.isInstanceOf(BadCredentialsException.class)
				.hasMessage("자격 증명에 실패하였습니다.");
	}
}
