package com.handwoong.everyonewaiter.user.infrastructure;

import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.UserRole;
import com.handwoong.everyonewaiter.user.domain.UserStatus;
import com.handwoong.everyonewaiter.user.domain.Username;
import org.junit.jupiter.api.Test;

class UserEntityTest {

	@Test
	void Should_CreateEntity_When_FromModel() {
		// given
		final User user = aUser().build();

		// when
		final UserEntity userEntity = UserEntity.from(user);

		// then
		assertThat(userEntity).extracting("id").isEqualTo(1L);
	}

	@Test
	void Should_CreateDomain_When_ToModel() {
		// given
		final User user = aUser().build();
		final UserEntity userEntity = UserEntity.from(user);

		// when
		final User result = userEntity.toModel();

		// then
		assertThat(result.getId().value()).isEqualTo(1L);
	}

	@Test
	void Should_ThrowException_When_FromModelUsernameIsNull() {
		// given
		final User user = aUser().username(null).build();

		// expect
		assertThatThrownBy(() -> UserEntity.from(user))
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	void Should_ThrowException_When_FromModelPasswordIsNull() {
		// given
		final User user = User.builder()
				.id(new UserId(1L))
				.username(new Username("handwoong"))
				.phoneNumber(new PhoneNumber("01012345678"))
				.role(UserRole.ROLE_USER)
				.status(UserStatus.ACTIVE)
				.build();

		// expect
		assertThatThrownBy(() -> UserEntity.from(user))
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	void Should_ThrowException_When_FromModelPhoneNumberIsNull() {
		// given
		final User user = aUser().phoneNumber(null).build();

		// expect
		assertThatThrownBy(() -> UserEntity.from(user))
				.isInstanceOf(NullPointerException.class);
	}
}
