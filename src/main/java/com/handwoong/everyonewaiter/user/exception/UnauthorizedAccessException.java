package com.handwoong.everyonewaiter.user.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;

public class UnauthorizedAccessException extends BaseException {

    public UnauthorizedAccessException() {
        super("인증에 실패하였습니다. 다시 로그인 해주세요.");
    }
}
