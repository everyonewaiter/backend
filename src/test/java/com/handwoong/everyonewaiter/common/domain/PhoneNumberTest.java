package com.handwoong.everyonewaiter.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.exception.InvalidPhoneNumberFormatException;
import org.junit.jupiter.api.Test;

class PhoneNumberTest {

	@Test
	void Should_Create_When_Valid() {
		// given
		final String phoneNumber = "01012345678";

		// when
		final PhoneNumber result = new PhoneNumber(phoneNumber);

		// then
		assertThat(result).hasToString(phoneNumber);
	}

	@Test
	void Should_ThrowException_When_Blank() {
		// expect
		assertThatThrownBy(() -> new PhoneNumber("  "))
				.isInstanceOf(InvalidPhoneNumberFormatException.class)
				.hasMessage("휴대폰 번호를 입력해주세요.");
	}

	@Test
	void Should_ThrowException_When_Null() {
		// expect
		assertThatThrownBy(() -> new PhoneNumber(null))
				.isInstanceOf(InvalidPhoneNumberFormatException.class)
				.hasMessage("휴대폰 번호를 입력해주세요.");
	}

	@Test
	void Should_ThrowException_When_InvalidFormat() {
		// expect
		assertThatThrownBy(() -> new PhoneNumber("01012345678910"))
				.isInstanceOf(InvalidPhoneNumberFormatException.class)
				.hasMessage("휴대폰 번호의 형식이 옳바르지 않습니다.");
	}
}
