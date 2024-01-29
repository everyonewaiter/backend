package com.handwoong.everyonewaiter.user.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class AlreadyExistsUsernameException extends BaseException {

    private final String username;

    public AlreadyExistsUsernameException(final String message, final String username) {
        super(message);
        this.username = username;
    }
}
