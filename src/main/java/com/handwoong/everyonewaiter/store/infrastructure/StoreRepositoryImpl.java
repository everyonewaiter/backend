package com.handwoong.everyonewaiter.store.infrastructure;

import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {

    private final StoreJpaRepository storeJpaRepository;

    @Override
    public Store save(final Store store) {
        return storeJpaRepository.save(StoreEntity.from(store)).toModel();
    }

    @Override
    public Store findByIdAndUserIdOrElseThrow(final StoreId storeId, final UserId userId) {
        return storeJpaRepository.findByIdAndUserId(storeId.value(), userId.value())
            .orElseThrow(() ->
                new StoreNotFoundException("매장을 찾을 수 없습니다.", "storeId : [" + storeId + "] userId : [" + userId + "]"))
            .toModel();
    }

    @Override
    public void delete(final Store store) {
        storeJpaRepository.delete(StoreEntity.from(store));
    }
}
