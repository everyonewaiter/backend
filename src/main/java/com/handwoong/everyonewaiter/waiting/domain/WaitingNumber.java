package com.handwoong.everyonewaiter.waiting.domain;

public record WaitingNumber(int value) {

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
