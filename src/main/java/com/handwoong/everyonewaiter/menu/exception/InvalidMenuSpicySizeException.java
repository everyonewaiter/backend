package com.handwoong.everyonewaiter.menu.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidMenuSpicySizeException extends BaseException {

	private final int spicy;

	public InvalidMenuSpicySizeException(final String message, final int spicy) {
		super(message);
		this.spicy = spicy;
	}
}
