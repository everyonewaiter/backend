package com.handwoong.everyonewaiter.category.domain;

import com.handwoong.everyonewaiter.category.dto.CategoryCreate;
import com.handwoong.everyonewaiter.category.dto.CategoryUpdate;
import com.handwoong.everyonewaiter.common.domain.AggregateRoot;
import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category extends AggregateRoot {

    private final CategoryId id;
    private final StoreId storeId;
    private final CategoryName name;
    private final CategoryIcon icon;
    private final DomainTimestamp timestamp;

    public static Category create(final CategoryCreate categoryCreate, final CategoryValidator categoryValidator) {
        categoryValidator.validate(categoryCreate.storeId());
        return Category.builder()
            .storeId(categoryCreate.storeId())
            .name(categoryCreate.name())
            .icon(categoryCreate.icon())
            .build();
    }

    public Category update(final CategoryUpdate categoryUpdate, final CategoryValidator categoryValidator) {
        categoryValidator.validate(categoryUpdate.storeId());
        return Category.builder()
            .id(id)
            .storeId(storeId)
            .name(categoryUpdate.name())
            .icon(categoryUpdate.icon())
            .build();
    }
}
