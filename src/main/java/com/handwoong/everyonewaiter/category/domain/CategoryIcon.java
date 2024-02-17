package com.handwoong.everyonewaiter.category.domain;

import com.handwoong.everyonewaiter.store.exception.InvalidStoreNameFormatException;
import org.springframework.util.StringUtils;

public record CategoryIcon(String icon) {

    public static final String CATEGORY_ICON_EMPTY_MESSAGE = "카테고리 아이콘을 등록해주세요.";
    public static final String CATEGORY_ICON_MAX_LENGTH_MESSAGE = "카테고리 아이콘은 20자 이하로 입력해주세요.";
    public static final int CATEGORY_ICON_MAX_LENGTH = 20;

    public CategoryIcon {
        validateNotEmpty(icon);
        validateLength(icon);
    }

    private void validateNotEmpty(final String icon) {
        if (!StringUtils.hasText(icon)) {
            throw new InvalidStoreNameFormatException(CATEGORY_ICON_EMPTY_MESSAGE, icon);
        }
    }

    private void validateLength(final String icon) {
        if (icon.length() > CATEGORY_ICON_MAX_LENGTH) {
            throw new InvalidStoreNameFormatException(CATEGORY_ICON_MAX_LENGTH_MESSAGE, icon);
        }
    }

    @Override
    public String toString() {
        return icon;
    }
}
