package com.handwoong.everyonewaiter.user.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidUsernameFormatException extends BaseException {

	private final String username;

	public InvalidUsernameFormatException(final String message, final String username) {
		super(message);
		this.username = username;
	}
}
