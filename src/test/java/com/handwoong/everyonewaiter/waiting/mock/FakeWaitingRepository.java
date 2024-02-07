package com.handwoong.everyonewaiter.waiting.mock;

import com.handwoong.everyonewaiter.waiting.application.port.WaitingRepository;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FakeWaitingRepository implements WaitingRepository {

    private final Map<Long, Waiting> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Waiting save(final Waiting waiting) {
        final Long id = Objects.nonNull(waiting.getId()) ? waiting.getId().value() : sequence++;
        final Waiting newWaiting = create(id, waiting);
        database.put(id, newWaiting);
        return newWaiting;
    }

    private Waiting create(final Long id, final Waiting waiting) {
        return Waiting.builder()
            .id(new WaitingId(id))
            .storeId(waiting.getStoreId())
            .adult(waiting.getAdult())
            .children(waiting.getChildren())
            .number(waiting.getNumber())
            .turn(waiting.getTurn())
            .phoneNumber(waiting.getPhoneNumber())
            .status(waiting.getStatus())
            .notificationType(waiting.getNotificationType())
            .uniqueCode(waiting.getUniqueCode())
            .build();
    }
}
