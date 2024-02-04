package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.store.infrastructure.StoreBusinessTimeEntity;
import java.util.List;
import java.util.Map;

public class StoreBusinessTimes {

    private final List<StoreBusinessTime> businessTimes;

    public StoreBusinessTimes(final List<StoreBusinessTime> businessTimes) {
        validate(businessTimes);
        this.businessTimes = businessTimes;
    }

    private void validate(final List<StoreBusinessTime> businessTimes) {
        if (getTotalDays(businessTimes) > DayOfWeek.size()) {
            throw new IllegalArgumentException("영업 시간의 요일은 " + DayOfWeek.size() + "개까지 선택할 수 있습니다.");
        }
        if (hasDuplicateDays(businessTimes)) {
            throw new IllegalArgumentException("영업 시간에 중복된 요일이 있습니다.");
        }
    }

    private int getTotalDays(final List<StoreBusinessTime> businessTimes) {
        return businessTimes.stream()
            .map(StoreBusinessTime::getDaysSize)
            .reduce(0, Integer::sum);
    }

    private boolean hasDuplicateDays(final List<StoreBusinessTime> businessTimes) {
        final Map<DayOfWeek, Integer> counter = DayOfWeek.dayOfWeekCounter();
        businessTimes.forEach(businessTime -> businessTime.daysCount(counter));
        return counter.values().stream().anyMatch(value -> value > 1);
    }

    public List<StoreBusinessTimeEntity> toEntity() {
        return businessTimes.stream()
            .map(StoreBusinessTimeEntity::from)
            .toList();
    }
}
