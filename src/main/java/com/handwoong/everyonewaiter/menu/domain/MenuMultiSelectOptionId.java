package com.handwoong.everyonewaiter.menu.domain;

public record MenuMultiSelectOptionId(Long value) {

	@Override
	public String toString() {
		return value.toString();
	}
}
