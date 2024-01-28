package com.handwoong.everyonewaiter.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
    ROOT("/"),
    ;

    private final String uri;
}
