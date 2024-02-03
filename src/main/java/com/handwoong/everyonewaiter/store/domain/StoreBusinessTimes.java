package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.store.infrastructure.StoreBusinessTimeEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoreBusinessTimes {

    private final List<StoreBusinessTime> businessTimes;

    public List<StoreBusinessTimeEntity> toEntity() {
        return businessTimes.stream()
            .map(StoreBusinessTimeEntity::from)
            .toList();
    }
}
