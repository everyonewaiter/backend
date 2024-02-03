package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.store.infrastructure.StoreBreakTimeEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoreBreakTimes {

    private final List<StoreBreakTime> breakTimes;

    public List<StoreBreakTimeEntity> toEntity() {
        return breakTimes.stream()
            .map(StoreBreakTimeEntity::from)
            .toList();
    }
}
