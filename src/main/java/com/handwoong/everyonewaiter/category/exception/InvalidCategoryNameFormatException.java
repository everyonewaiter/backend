package com.handwoong.everyonewaiter.category.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidCategoryNameFormatException extends BaseException {

	private final String name;

	public InvalidCategoryNameFormatException(final String message, final String name) {
		super(message);
		this.name = name;
	}
}
