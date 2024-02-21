package com.handwoong.everyonewaiter.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
	ROOT("/"),
	WAITING_CANCEL("/api/waiting/cancel"),
	;

	private final String uri;
}
