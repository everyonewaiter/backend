package com.handwoong.everyonewaiter.menu.domain;

public record MenuId(Long value) {

	@Override
	public String toString() {
		return value.toString();
	}
}
