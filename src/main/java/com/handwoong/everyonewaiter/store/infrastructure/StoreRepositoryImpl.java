package com.handwoong.everyonewaiter.store.infrastructure;

import static com.handwoong.everyonewaiter.store.infrastructure.QStoreEntity.storeEntity;
import static com.handwoong.everyonewaiter.store.infrastructure.QStoreOptionEntity.storeOptionEntity;

import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {

	private final JPAQueryFactory queryFactory;
	private final StoreJpaRepository storeJpaRepository;

	@Override
	public List<Store> findAllByUserId(final UserId userId) {
		return queryFactory.selectFrom(storeEntity)
				.innerJoin(storeEntity.optionEntity, storeOptionEntity)
				.fetchJoin()
				.orderBy(storeEntity.createdAt.asc())
				.fetch()
				.stream()
				.map(StoreEntity::toModel)
				.toList();
	}

	@Override
	public Store save(final Store store) {
		return storeJpaRepository.save(StoreEntity.from(store)).toModel();
	}

	@Override
	public Store findByIdAndUserIdOrElseThrow(final StoreId storeId, final UserId userId) {
		return storeJpaRepository.findByIdAndUserId(storeId.value(), userId.value())
				.orElseThrow(() ->
						new StoreNotFoundException("매장을 찾을 수 없습니다.",
								"storeId : [" + storeId + "] userId : [" + userId + "]"))
				.toModel();
	}

	@Override
	public void delete(final Store store) {
		storeJpaRepository.delete(StoreEntity.from(store));
	}
}
