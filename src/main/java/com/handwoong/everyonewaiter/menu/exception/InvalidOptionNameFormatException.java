package com.handwoong.everyonewaiter.menu.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidOptionNameFormatException extends BaseException {
	
	private final String name;

	public InvalidOptionNameFormatException(final String message, final String name) {
		super(message);
		this.name = name;
	}
}
