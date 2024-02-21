package com.handwoong.everyonewaiter.common.exception;

public class InvalidJwtTokenException extends BaseException {

	public InvalidJwtTokenException(final Throwable cause) {
		super("유효하지 않은 토큰입니다.", cause);
	}
}
