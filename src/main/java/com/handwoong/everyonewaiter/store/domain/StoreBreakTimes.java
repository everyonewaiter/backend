package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.store.infrastructure.StoreBreakTimeEntity;
import java.util.List;
import java.util.Map;

public class StoreBreakTimes {

    private final List<StoreBreakTime> breakTimes;

    public StoreBreakTimes(final List<StoreBreakTime> breakTimes) {
        validate(breakTimes);
        this.breakTimes = breakTimes;
    }

    private void validate(final List<StoreBreakTime> breakTimes) {
        if (getTotalDays(breakTimes) > DayOfWeek.size()) {
            throw new IllegalArgumentException("브레이크 타임의 요일은 총 " + DayOfWeek.size() + "개까지 선택할 수 있습니다.");
        }
        if (hasDuplicateDays(breakTimes)) {
            throw new IllegalArgumentException("브레이크 타임에 중복된 요일이 있습니다.");
        }
    }

    private int getTotalDays(final List<StoreBreakTime> breakTimes) {
        return breakTimes.stream()
            .map(StoreBreakTime::getDaysSize)
            .reduce(0, Integer::sum);
    }

    private boolean hasDuplicateDays(final List<StoreBreakTime> breakTimes) {
        final Map<DayOfWeek, Integer> counter = DayOfWeek.dayOfWeekCounter();
        breakTimes.forEach(breakTime -> breakTime.daysCount(counter));
        return counter.values().stream().anyMatch(value -> value > 1);
    }

    public List<StoreBreakTimeEntity> toEntity() {
        return breakTimes.stream()
            .map(StoreBreakTimeEntity::from)
            .toList();
    }
}
