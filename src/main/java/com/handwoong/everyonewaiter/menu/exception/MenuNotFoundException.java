package com.handwoong.everyonewaiter.menu.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class MenuNotFoundException extends BaseException {

	private final String resource;

	public MenuNotFoundException(final String message, final String resource) {
		super(message);
		this.resource = resource;
	}
}
