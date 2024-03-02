package com.handwoong.everyonewaiter.waiting.controller.response;

import lombok.Builder;

@Builder
public record WaitingCountResponse(int count) {

	public static WaitingCountResponse from(final int count) {
		return WaitingCountResponse.builder()
				.count(count)
				.build();
	}
}
