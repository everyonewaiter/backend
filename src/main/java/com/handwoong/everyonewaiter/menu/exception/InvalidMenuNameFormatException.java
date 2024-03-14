package com.handwoong.everyonewaiter.menu.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidMenuNameFormatException extends BaseException {

	private final String name;

	public InvalidMenuNameFormatException(final String message, final String name) {
		super(message);
		this.name = name;
	}
}
