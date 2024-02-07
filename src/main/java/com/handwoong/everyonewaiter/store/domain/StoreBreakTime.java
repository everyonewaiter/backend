package com.handwoong.everyonewaiter.store.domain;

import java.time.LocalTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreBreakTime {

    private final StoreBreakTimeId id;
    private final LocalTime start;
    private final LocalTime end;
    private final StoreDaysOfWeek daysOfWeek;

    public void daysCount(final Map<DayOfWeek, Integer> counter) {
        daysOfWeek.count(counter);
    }

    public int getDaysSize() {
        return daysOfWeek.getDaysSize();
    }

    public boolean compareCurrentTime(final DayOfWeek dayOfWeek, final LocalTime currentTime) {
        if (daysOfWeek.contains(dayOfWeek)) {
            return currentTime.isAfter(start) && currentTime.isBefore(end);
        }
        return false;
    }
}
