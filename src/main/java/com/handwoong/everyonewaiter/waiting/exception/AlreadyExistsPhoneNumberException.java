package com.handwoong.everyonewaiter.waiting.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class AlreadyExistsPhoneNumberException extends BaseException {

	private final String phoneNumber;

	public AlreadyExistsPhoneNumberException(final String message, final String phoneNumber) {
		super(message);
		this.phoneNumber = phoneNumber;
	}
}
