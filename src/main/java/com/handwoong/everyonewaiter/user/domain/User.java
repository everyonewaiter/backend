package com.handwoong.everyonewaiter.user.domain;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import lombok.Builder;

@Builder
public class User {

    private final Username username;
    private final Password password;
    private final PhoneNumber phoneNumber;
    private final UserRole role;
    private final UserStatus status;
}