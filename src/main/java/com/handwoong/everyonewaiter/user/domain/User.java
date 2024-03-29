package com.handwoong.everyonewaiter.user.domain;

import com.handwoong.everyonewaiter.common.application.port.TimeHolder;
import com.handwoong.everyonewaiter.common.domain.AggregateRoot;
import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
public class User extends AggregateRoot {

	private final UserId id;
	private final Username username;
	private final Password password;
	private final PhoneNumber phoneNumber;
	private final UserRole role;
	private final UserStatus status;
	private final Long lastLoggedIn;
	private final DomainTimestamp timestamp;

	public static User create(final UserJoin userJoin, final PasswordEncoder passwordEncoder) {
		return User.builder()
				.username(userJoin.username())
				.password(userJoin.password().encode(passwordEncoder))
				.phoneNumber(userJoin.phoneNumber())
				.role(UserRole.ROLE_USER)
				.status(UserStatus.ACTIVE)
				.build();
	}

	public User login(final TimeHolder timeHolder) {
		return User.builder()
				.id(id)
				.username(username)
				.password(password)
				.phoneNumber(phoneNumber)
				.role(role)
				.status(status)
				.lastLoggedIn(timeHolder.millis())
				.timestamp(timestamp)
				.build();
	}

	public boolean checkStatusDifference(final UserStatus status) {
		return this.status != status;
	}
}
