package com.handwoong.everyonewaiter.store.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class StoreNotFoundException extends BaseException {

    private final String resource;

    public StoreNotFoundException(final String message, final String resource) {
        super(message);
        this.resource = resource;
    }
}
