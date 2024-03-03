package com.handwoong.everyonewaiter.waiting.mock;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.application.port.WaitingRepository;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import com.handwoong.everyonewaiter.waiting.exception.WaitingNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

	@Override
	public boolean existsByPhoneNumber(final PhoneNumber phoneNumber) {
		return database.values()
				.stream()
				.filter(waiting -> waiting.getStatus().equals(WaitingStatus.WAIT))
				.anyMatch(waiting -> waiting.getPhoneNumber().equals(phoneNumber));
	}

	@Override
	public int countByAfterStoreOpen(
			final StoreId storeId,
			final WaitingStatus status,
			final LocalDateTime lastOpenedAt
	) {
		return Math.toIntExact(
				database.values()
						.stream()
						.filter(waiting -> waiting.getStoreId().equals(storeId))
						.filter(waiting -> Objects.isNull(status) || waiting.getStatus().equals(status))
						.filter(waiting -> waiting.getTimestamp().getCreatedAt().isAfter(lastOpenedAt))
						.count()
		);
	}

	@Override
	public int countByBeforeCreatedAt(final StoreId storeId, final LocalDateTime createdAt) {
		return Math.toIntExact(
				database.values()
						.stream()
						.filter(waiting -> waiting.getStoreId().equals(storeId))
						.filter(waiting -> waiting.getStatus().equals(WaitingStatus.WAIT))
						.filter(waiting -> waiting.getTimestamp().getCreatedAt().isBefore(createdAt))
						.count()
		);
	}

	@Override
	public Waiting findByStoreIdAndUniqueCodeOrElseThrow(final StoreId storeId, final UUID uniqueCode) {
		return database.values()
				.stream()
				.filter(waiting -> waiting.getStoreId().equals(storeId))
				.filter(waiting -> waiting.getUniqueCode().equals(uniqueCode))
				.findAny()
				.orElseThrow(() ->
						new WaitingNotFoundException("웨이팅을 찾을 수 없습니다.",
								"storeId : [" + storeId + "] uniqueCode : [" + uniqueCode + "]")
				);
	}

	private Waiting create(final Long id, final Waiting waiting) {
		return Waiting.builder()
				.id(new WaitingId(id))
				.storeId(waiting.getStoreId())
				.adult(waiting.getAdult())
				.children(waiting.getChildren())
				.number(waiting.getNumber())
				.phoneNumber(waiting.getPhoneNumber())
				.status(waiting.getStatus())
				.notificationType(waiting.getNotificationType())
				.uniqueCode(waiting.getUniqueCode())
				.timestamp(waiting.getTimestamp())
				.build();
	}
}
