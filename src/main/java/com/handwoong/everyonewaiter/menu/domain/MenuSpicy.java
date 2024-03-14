package com.handwoong.everyonewaiter.menu.domain;

import com.handwoong.everyonewaiter.menu.exception.InvalidMenuSpicySizeException;

public record MenuSpicy(int value) {

	public static final String MENU_SPICY_SIZE_MESSAGE = "메뉴의 맵기는 0이상 10이하로 입력해주세요.";
	public static final int MIN_SPICY = 0;
	public static final int MAX_SPICY = 10;

	public MenuSpicy {
		if (value < MIN_SPICY || value > MAX_SPICY) {
			throw new InvalidMenuSpicySizeException(MENU_SPICY_SIZE_MESSAGE, value);
		}
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
