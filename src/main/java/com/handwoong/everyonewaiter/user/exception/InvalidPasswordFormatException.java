package com.handwoong.everyonewaiter.user.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;

public class InvalidPasswordFormatException extends BaseException {

	public InvalidPasswordFormatException() {
		super("비밀번호는 6자리 숫자로 입력해주세요.");
	}
}
