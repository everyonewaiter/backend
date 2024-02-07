package com.handwoong.everyonewaiter.waiting.infrastructure;

import static com.handwoong.everyonewaiter.waiting.infrastructure.QWaitingEntity.waitingEntity;

import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.application.port.WaitingRepository;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WaitingRepositoryImpl implements WaitingRepository {

    private final JPAQueryFactory queryFactory;
    private final WaitingJpaRepository waitingJpaRepository;

    @Override
    public Waiting save(final Waiting waiting) {
        return waitingJpaRepository.save(WaitingEntity.from(waiting)).toModel();
    }

    @Override
    public int countByAfterStoreOpen(
        final StoreId storeId,
        final WaitingStatus status,
        final LocalDateTime lastOpenedAt
    ) {
        return Math.toIntExact(
            queryFactory.select(waitingEntity.count())
                .from(waitingEntity)
                .where(
                    storeIdEq(storeId),
                    statusEq(status),
                    afterCreatedAt(lastOpenedAt)
                )
                .fetchFirst()
        );
    }

    private BooleanExpression storeIdEq(final StoreId storeId) {
        return Objects.isNull(storeId) ? null : waitingEntity.storeId.eq(storeId.value());
    }

    private BooleanExpression statusEq(final WaitingStatus status) {
        return Objects.isNull(status) ? null : waitingEntity.status.eq(status);
    }

    private BooleanExpression afterCreatedAt(final LocalDateTime timestamp) {
        return Objects.isNull(timestamp) ? null : waitingEntity.createdAt.after(timestamp);
    }
}
