package com.handwoong.everyonewaiter.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AnonymousAllowUri implements AllowUri {
    USER_JOIN("/api/users"),
    USER_LOGIN("/api/users/login"),
    ;

    private final String uri;
}
