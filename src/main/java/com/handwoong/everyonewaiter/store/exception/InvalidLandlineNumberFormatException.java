package com.handwoong.everyonewaiter.store.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidLandlineNumberFormatException extends BaseException {

	private final String landlineNumber;

	public InvalidLandlineNumberFormatException(final String message, final String landlineNumber) {
		super(message);
		this.landlineNumber = landlineNumber;
	}
}
