package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import java.time.LocalTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreBusinessTime {

    private final StoreBusinessTimeId id;
    private final LocalTime open;
    private final LocalTime close;
    private final StoreDaysOfWeek daysOfWeek;
    private final DomainTimestamp timestamp;

    public void daysCount(final Map<DayOfWeek, Integer> counter) {
        daysOfWeek.count(counter);
    }

    public int getDaysSize() {
        return daysOfWeek.getDaysSize();
    }
}
