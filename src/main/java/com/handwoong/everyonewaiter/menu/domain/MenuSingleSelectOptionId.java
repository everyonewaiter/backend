package com.handwoong.everyonewaiter.menu.domain;

public record MenuSingleSelectOptionId(Long value) {

	@Override
	public String toString() {
		return value.toString();
	}
}
