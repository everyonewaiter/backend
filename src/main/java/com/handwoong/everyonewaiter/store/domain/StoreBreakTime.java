package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreBreakTime {

    private final StoreBreakTimeId id;
    private final LocalTime start;
    private final LocalTime end;
    private final StoreEventDaysOfWeek daysOfWeek;
    private final DomainTimestamp timestamp;
}
