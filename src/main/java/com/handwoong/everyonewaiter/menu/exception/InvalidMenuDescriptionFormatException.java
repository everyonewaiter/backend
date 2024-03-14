package com.handwoong.everyonewaiter.menu.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidMenuDescriptionFormatException extends BaseException {

	private final String description;


	public InvalidMenuDescriptionFormatException(final String message, final String description) {
		super(message);
		this.description = description;
	}
}
