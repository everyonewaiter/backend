package com.handwoong.everyonewaiter.waiting.infrastructure;

import static com.handwoong.everyonewaiter.waiting.infrastructure.QWaitingEntity.waitingEntity;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.application.port.WaitingRepository;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import com.handwoong.everyonewaiter.waiting.exception.WaitingNotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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
    public boolean existsByPhoneNumber(final PhoneNumber phoneNumber) {
        return Objects.nonNull(
            queryFactory.selectOne()
                .from(waitingEntity)
                .where(
                    statusEq(WaitingStatus.WAIT),
                    phoneNumberEq(phoneNumber)
                )
                .fetchFirst()
        );
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

    @Override
    public Waiting findByStoreIdAndUniqueCodeOrElseThrow(final StoreId storeId, final UUID uniqueCode) {
        return Optional.ofNullable(
                queryFactory.selectFrom(waitingEntity)
                    .where(
                        storeIdEq(storeId),
                        waitingEntity.uniqueCode.eq(uniqueCode)
                    )
                    .fetchFirst()
            )
            .orElseThrow(() ->
                new WaitingNotFoundException("웨이팅을 찾을 수 없습니다.",
                    "storeId : [" + storeId + "] uniqueCode : [" + uniqueCode + "]")
            )
            .toModel();
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

    private BooleanExpression phoneNumberEq(final PhoneNumber phoneNumber) {
        return Objects.isNull(phoneNumber) ? null : waitingEntity.phoneNumber.eq(phoneNumber.toString());
    }
}
