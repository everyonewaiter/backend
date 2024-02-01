package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreBusinessTime {

    private final StoreBusinessTimeId id;
    private final LocalTime open;
    private final LocalTime close;
    private final StoreEventDaysOfWeek daysOfWeek;
    private final DomainTimestamp timestamp;
}
