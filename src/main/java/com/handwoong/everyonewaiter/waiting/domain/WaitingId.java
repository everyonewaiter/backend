package com.handwoong.everyonewaiter.waiting.domain;

public record WaitingId(Long value) {

	@Override
	public String toString() {
		return value.toString();
	}
}
