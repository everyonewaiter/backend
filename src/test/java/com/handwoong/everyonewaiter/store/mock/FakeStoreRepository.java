package com.handwoong.everyonewaiter.store.mock;

import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FakeStoreRepository implements StoreRepository {

    private final Map<Long, Store> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Store save(final Store store) {
        final Long id = Objects.nonNull(store.getId()) ? store.getId().value() : sequence++;
        final Store newStore = create(id, store);
        database.put(id, newStore);
        return newStore;
    }

    private Store create(final Long id, final Store store) {
        return Store.builder()
            .id(new StoreId(id))
            .userId(store.getUserId())
            .name(store.getName())
            .landlineNumber(store.getLandlineNumber())
            .status(store.getStatus())
            .lastOpenedAt(store.getLastOpenedAt())
            .lastClosedAt(store.getLastClosedAt())
            .breakTimes(store.getBreakTimes())
            .businessTimes(store.getBusinessTimes())
            .option(store.getOption())
            .timestamp(store.getTimestamp())
            .build();
    }
}
