package com.handwoong.everyonewaiter.category.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class CategoryNotFoundException extends BaseException {

	private final String resource;

	public CategoryNotFoundException(final String message, final String resource) {
		super(message);
		this.resource = resource;
	}
}
