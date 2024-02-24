package com.handwoong.everyonewaiter.store.mock;

import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.UserId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakeStoreRepository implements StoreRepository {

	private final Map<Long, Store> database = new HashMap<>();

	private Long sequence = 1L;

	@Override
	public List<Store> findAllByUserId(final UserId userId) {
		return database.values()
				.stream()
				.filter(store -> store.getUserId().equals(userId))
				.toList();
	}

	@Override
	public Store save(final Store store) {
		final Long id = Objects.nonNull(store.getId()) ? store.getId().value() : sequence++;
		final Store newStore = create(id, store);
		database.put(id, newStore);
		return newStore;
	}

	@Override
	public Store findByIdAndUserIdOrElseThrow(final StoreId storeId, final UserId userId) {
		return Optional.ofNullable(database.get(storeId.value()))
				.stream()
				.filter(store -> store.getUserId().equals(userId))
				.findAny()
				.orElseThrow(() ->
						new StoreNotFoundException("매장을 찾을 수 없습니다.",
								"storeId : [" + storeId + "] userId : [" + userId + "]"));
	}

	@Override
	public void delete(final Store store) {
		database.remove(store.getId().value());
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
