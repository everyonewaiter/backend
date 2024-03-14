package com.handwoong.everyonewaiter.menu.domain;

public record MenuOptionGroupId(Long value) {

	@Override
	public String toString() {
		return value.toString();
	}
}
