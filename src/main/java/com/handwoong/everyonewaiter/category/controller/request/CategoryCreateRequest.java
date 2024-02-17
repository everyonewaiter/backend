package com.handwoong.everyonewaiter.category.controller.request;

import static com.handwoong.everyonewaiter.category.domain.CategoryIcon.CATEGORY_ICON_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.category.domain.CategoryIcon.CATEGORY_ICON_MAX_LENGTH;
import static com.handwoong.everyonewaiter.category.domain.CategoryIcon.CATEGORY_ICON_MAX_LENGTH_MESSAGE;
import static com.handwoong.everyonewaiter.category.domain.CategoryName.CATEGORY_NAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.category.domain.CategoryName.CATEGORY_NAME_MAX_LENGTH;
import static com.handwoong.everyonewaiter.category.domain.CategoryName.CATEGORY_NAME_MAX_LENGTH_MESSAGE;

import com.handwoong.everyonewaiter.category.domain.CategoryIcon;
import com.handwoong.everyonewaiter.category.domain.CategoryName;
import com.handwoong.everyonewaiter.category.dto.CategoryCreate;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(

    @NotNull
    Long storeId,

    @NotBlank(message = CATEGORY_NAME_EMPTY_MESSAGE)
    @Size(max = CATEGORY_NAME_MAX_LENGTH, message = CATEGORY_NAME_MAX_LENGTH_MESSAGE)
    String name,

    @NotBlank(message = CATEGORY_ICON_EMPTY_MESSAGE)
    @Size(max = CATEGORY_ICON_MAX_LENGTH, message = CATEGORY_ICON_MAX_LENGTH_MESSAGE)
    String icon
) {

    public CategoryCreate toDomainDto() {
        return CategoryCreate.builder()
            .storeId(new StoreId(storeId))
            .name(new CategoryName(name))
            .icon(new CategoryIcon(icon))
            .build();
    }
}
