package com.handwoong.everyonewaiter.category.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidCategoryIconFormatException extends BaseException {

	private final String icon;

	public InvalidCategoryIconFormatException(final String message, final String icon) {
		super(message);
		this.icon = icon;
	}
}
