package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.common.application.port.TimeHolder;
import com.handwoong.everyonewaiter.common.domain.AggregateRoot;
import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import com.handwoong.everyonewaiter.store.dto.StoreCreate;
import com.handwoong.everyonewaiter.store.dto.StoreOptionUpdate;
import com.handwoong.everyonewaiter.store.dto.StoreUpdate;
import com.handwoong.everyonewaiter.user.domain.UserId;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Store extends AggregateRoot {

    private final StoreId id;
    private final UserId userId;
    private final StoreName name;
    private final LandlineNumber landlineNumber;
    private final StoreStatus status;
    private final LocalDateTime lastOpenedAt;
    private final LocalDateTime lastClosedAt;
    private final StoreBreakTimes breakTimes;
    private final StoreBusinessTimes businessTimes;
    private final StoreOption option;
    private final DomainTimestamp timestamp;

    public static Store create(final UserId userId, final StoreCreate storeCreate) {
        return Store.builder()
            .userId(userId)
            .name(storeCreate.name())
            .landlineNumber(storeCreate.landlineNumber())
            .status(StoreStatus.CLOSE)
            .breakTimes(storeCreate.breakTimes())
            .businessTimes(storeCreate.businessTimes())
            .option(storeCreate.option())
            .build();
    }

    public Store update(final StoreUpdate storeUpdate) {
        return Store.builder()
            .id(id)
            .userId(userId)
            .name(storeUpdate.name())
            .landlineNumber(storeUpdate.landlineNumber())
            .status(status)
            .lastOpenedAt(lastOpenedAt)
            .lastClosedAt(lastClosedAt)
            .breakTimes(storeUpdate.breakTimes())
            .businessTimes(storeUpdate.businessTimes())
            .option(option)
            .timestamp(timestamp)
            .build();
    }

    public Store update(final StoreOptionUpdate storeOptionUpdate) {
        return Store.builder()
            .id(id)
            .userId(userId)
            .name(name)
            .landlineNumber(landlineNumber)
            .status(status)
            .lastOpenedAt(lastOpenedAt)
            .lastClosedAt(lastClosedAt)
            .breakTimes(breakTimes)
            .businessTimes(businessTimes)
            .option(option.update(storeOptionUpdate))
            .timestamp(timestamp)
            .build();
    }

    public boolean isOpen() {
        return status == StoreStatus.OPEN;
    }

    public boolean isWithinBusinessTime(final TimeHolder timeHolder) {
        return businessTimes.isWithinBusinessTime(timeHolder);
    }

    public boolean isWithinBreakTime(final TimeHolder timeHolder) {
        return breakTimes.isWithinBreakTime(timeHolder);
    }

    public boolean isUseWaiting() {
        return option.isUseWaiting();
    }

    public boolean isUseBreakTime() {
        return option.isUseBreakTime();
    }
}
