package com.handwoong.everyonewaiter.user.domain;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
public class User {

    private final Long id;
    private final Username username;
    private final Password password;
    private final PhoneNumber phoneNumber;
    private final UserRole role;
    private final UserStatus status;

    public static User create(final UserJoin userJoin, final PasswordEncoder passwordEncoder) {
        return User.builder()
            .username(userJoin.username())
            .password(userJoin.password().encode(passwordEncoder))
            .phoneNumber(userJoin.phoneNumber())
            .role(UserRole.ROLE_USER)
            .status(UserStatus.ACTIVE)
            .build();
    }
}
