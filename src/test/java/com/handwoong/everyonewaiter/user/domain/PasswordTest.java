package com.handwoong.everyonewaiter.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.mock.FakePasswordEncoder;
import com.handwoong.everyonewaiter.user.exception.InvalidPasswordFormatException;
import org.junit.jupiter.api.Test;

class PasswordTest {

	@Test
	void Should_Create_When_Valid() {
		// given
		final String password = "111111";

		// when
		final Password result = new Password(password);

		// then
		assertThat(result).hasToString(password);
	}

	@Test
	void Should_ThrowException_When_Null() {
		// expect
		assertThatThrownBy(() -> new Password(null))
				.isInstanceOf(InvalidPasswordFormatException.class)
				.hasMessage("비밀번호는 6자리 숫자로 입력해주세요.");
	}

	@Test
	void Should_ThrowException_When_Blank() {
		// expect
		assertThatThrownBy(() -> new Password("  "))
				.isInstanceOf(InvalidPasswordFormatException.class)
				.hasMessage("비밀번호는 6자리 숫자로 입력해주세요.");
	}

	@Test
	void Should_Encoded_When_Encode() {
		// given
		final FakePasswordEncoder passwordEncoder = new FakePasswordEncoder("encode");
		final Password password = new Password("111111");

		// when
		final Password encodedPassword = password.encode(passwordEncoder);

		// then
		assertThat(encodedPassword).hasToString("111111" + "encode");
	}
}
