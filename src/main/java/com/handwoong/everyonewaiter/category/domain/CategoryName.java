package com.handwoong.everyonewaiter.category.domain;

import com.handwoong.everyonewaiter.store.exception.InvalidStoreNameFormatException;
import org.springframework.util.StringUtils;

public record CategoryName(String name) {

    public static final String CATEGORY_NAME_EMPTY_MESSAGE = "카테고리 이름을 입력해주세요.";
    public static final String CATEGORY_NAME_MAX_LENGTH_MESSAGE = "카테고리 이름은 20자 이하로 입력해주세요.";
    public static final int CATEGORY_NAME_MAX_LENGTH = 20;

    public CategoryName {
        validateNotEmpty(name);
        validateLength(name);
    }

    private void validateNotEmpty(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new InvalidStoreNameFormatException(CATEGORY_NAME_EMPTY_MESSAGE, name);
        }
    }

    private void validateLength(final String name) {
        if (name.length() > CATEGORY_NAME_MAX_LENGTH) {
            throw new InvalidStoreNameFormatException(CATEGORY_NAME_MAX_LENGTH_MESSAGE, name);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
