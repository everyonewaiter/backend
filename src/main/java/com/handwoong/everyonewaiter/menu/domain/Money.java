package com.handwoong.everyonewaiter.menu.domain;

public record Money(long value) {

	@Override
	public String toString() {
		return Long.toString(value);
	}
}
