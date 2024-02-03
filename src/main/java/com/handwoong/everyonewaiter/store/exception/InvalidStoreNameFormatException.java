package com.handwoong.everyonewaiter.store.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidStoreNameFormatException extends BaseException {

    private final String name;

    public InvalidStoreNameFormatException(final String message, final String name) {
        super(message);
        this.name = name;
    }
}
