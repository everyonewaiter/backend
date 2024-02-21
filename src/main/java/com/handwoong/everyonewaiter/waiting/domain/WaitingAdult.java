package com.handwoong.everyonewaiter.waiting.domain;

public record WaitingAdult(int value) {

	public static final int MIN_ADULT = 1;
	public static final int MAX_ADULT = 20;
	public static final String MIN_ADULT_MESSAGE = "어른의 최소 인원은 1이상으로 입력해 주세요.";
	public static final String MAX_ADULT_MESSAGE = "어른의 최대 인원은 20명 이하이어야 합니다.";

	public WaitingAdult {
		validate(value);
	}

	private void validate(final int value) {
		if (value < MIN_ADULT) {
			throw new IllegalArgumentException(MIN_ADULT_MESSAGE);
		}
		if (value > MAX_ADULT) {
			throw new IllegalArgumentException(MAX_ADULT_MESSAGE);
		}
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
