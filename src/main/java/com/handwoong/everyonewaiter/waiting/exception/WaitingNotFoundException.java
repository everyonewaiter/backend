package com.handwoong.everyonewaiter.waiting.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class WaitingNotFoundException extends BaseException {

	private final String resource;

	public WaitingNotFoundException(final String message, final String resource) {
		super(message);
		this.resource = resource;
	}
}
