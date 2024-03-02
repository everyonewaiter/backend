package com.handwoong.everyonewaiter.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
	ROOT("/"),
	WAITING_CUSTOMER("/api/waiting/customer"),
	WAITING_TURN("/api/waiting/turn"),
	WAITING_CANCEL("/api/waiting/cancel"),
	;

	private final String uri;
}
