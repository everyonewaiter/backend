package com.handwoong.everyonewaiter.category.dto;

import com.handwoong.everyonewaiter.category.domain.CategoryIcon;
import com.handwoong.everyonewaiter.category.domain.CategoryName;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import lombok.Builder;

@Builder
public record CategoryCreate(StoreId storeId, CategoryName name, CategoryIcon icon) {

}
