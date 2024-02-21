package com.handwoong.everyonewaiter.common.exception;

import lombok.Getter;

@Getter
public class InvalidPhoneNumberFormatException extends BaseException {

	private final String phoneNumber;

	public InvalidPhoneNumberFormatException(final String message, final String phoneNumber) {
		super(message);
		this.phoneNumber = phoneNumber;
	}
}
