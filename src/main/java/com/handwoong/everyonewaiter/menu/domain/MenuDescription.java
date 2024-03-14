package com.handwoong.everyonewaiter.menu.domain;

import com.handwoong.everyonewaiter.menu.exception.InvalidMenuDescriptionFormatException;
import org.springframework.util.StringUtils;

public record MenuDescription(String description) {

	public static final String MENU_DESCRIPTION_MAX_LENGTH_MESSAGE = "메뉴 설명은 100자 이하로 입력해주세요.";
	public static final int MENU_DESCRIPTION_MAX_LENGTH = 100;

	public MenuDescription(final String description) {
		final String trimmedDescription = description.trim();
		validateLength(trimmedDescription);
		this.description = trimmedDescription;
	}

	private void validateLength(final String description) {
		if (StringUtils.hasText(description) && description.length() > MENU_DESCRIPTION_MAX_LENGTH) {
			throw new InvalidMenuDescriptionFormatException(MENU_DESCRIPTION_MAX_LENGTH_MESSAGE, description);
		}
	}

	@Override
	public String toString() {
		return description;
	}
}
