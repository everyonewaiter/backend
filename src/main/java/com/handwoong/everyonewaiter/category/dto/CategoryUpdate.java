package com.handwoong.everyonewaiter.category.dto;

import com.handwoong.everyonewaiter.category.domain.CategoryIcon;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.domain.CategoryName;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import lombok.Builder;

@Builder
public record CategoryUpdate(CategoryId id, StoreId storeId, CategoryName name, CategoryIcon icon) {

}
