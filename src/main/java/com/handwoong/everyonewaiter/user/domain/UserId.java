package com.handwoong.everyonewaiter.user.domain;

public record UserId(Long value) {

	@Override
	public String toString() {
		return value.toString();
	}
}
