package com.handwoong.everyonewaiter.user.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class UserNotFoundException extends BaseException {

    private final String resource;

    public UserNotFoundException(final String message, final String resource) {
        super(message);
        this.resource = resource;
    }
}
