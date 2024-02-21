package com.handwoong.everyonewaiter.user.application;

import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import com.handwoong.everyonewaiter.user.dto.UserLogin;
import com.handwoong.everyonewaiter.user.exception.AlreadyExistsUsernameException;
import com.handwoong.everyonewaiter.util.TestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

class UserServiceImplTest {

	@Test
	void Should_Join_When_ValidUserJoin() {
		// given
		final TestContainer testContainer = new TestContainer();
		final UserJoin userJoin = UserJoin.builder()
				.username(new Username("handwoong"))
				.password(new Password("password"))
				.phoneNumber(new PhoneNumber("01012345678"))
				.build();

		// when
		final UserId userId = testContainer.userService.join(userJoin);

		// then
		assertThat(userId.value()).isEqualTo(1L);
	}

	@Test
	void Should_ThrowException_When_DuplicateUsername() {
		// given
		final TestContainer testContainer = new TestContainer();
		final User user = aUser().build();
		testContainer.userRepository.save(user);

		final UserJoin userJoin = UserJoin.builder()
				.username(new Username("handwoong"))
				.password(new Password("password"))
				.phoneNumber(new PhoneNumber("01012345678"))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.userService.join(userJoin))
				.isInstanceOf(AlreadyExistsUsernameException.class)
				.hasMessage("이미 존재하는 사용자 아이디입니다.");
	}

	@Test
	void Should_GetJwtToken_When_Login() {
		// given
		final TestContainer testContainer = new TestContainer();
		final Username username = new Username("handwoong");
		final Password password = new Password("password");
		final User user = aUser().username(username).password(password).build();
		testContainer.userRepository.save(user);

		final UserLogin userLogin = UserLogin.builder()
				.username(username)
				.password(password)
				.build();

		// when
		final JwtToken accessToken = testContainer.userService.login(userLogin);

		// then
		assertThat(accessToken).extracting("token").isEqualTo("accessToken");
	}

	@Test
	void Should_ThrowException_When_UsernameNotFound() {
		// given
		final TestContainer testContainer = new TestContainer();
		final UserLogin userLogin = UserLogin.builder()
				.username(new Username("handwoong"))
				.password(new Password("123456"))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.userService.login(userLogin))
				.isInstanceOf(BadCredentialsException.class)
				.hasMessage("자격 증명에 실패하였습니다.");
	}

	@Test
	void Should_ThrowException_When_PasswordNotMatched() {
		// given
		final TestContainer testContainer = new TestContainer();
		final Username username = new Username("handwoong");
		final User user = aUser().username(username).build();
		testContainer.userRepository.save(user);

		final UserLogin userLogin = UserLogin.builder()
				.username(username)
				.password(new Password("123456"))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.userService.login(userLogin))
				.isInstanceOf(BadCredentialsException.class)
				.hasMessage("자격 증명에 실패하였습니다.");
	}
}
