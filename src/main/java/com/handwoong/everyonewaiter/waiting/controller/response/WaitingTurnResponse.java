package com.handwoong.everyonewaiter.waiting.controller.response;

import lombok.Builder;

@Builder
public record WaitingTurnResponse(int turn) {

	public static WaitingTurnResponse from(final int turn) {
		return WaitingTurnResponse.builder()
				.turn(turn)
				.build();
	}
}
