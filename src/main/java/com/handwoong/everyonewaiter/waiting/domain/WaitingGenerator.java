package com.handwoong.everyonewaiter.waiting.domain;

import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.application.port.UserRepository;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.infrastructure.SecurityUtils;
import com.handwoong.everyonewaiter.waiting.application.port.WaitingRepository;
import com.handwoong.everyonewaiter.waiting.dto.WaitingGenerateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingGenerator {

	private final UserRepository userRepository;
	private final StoreRepository storeRepository;
	private final WaitingRepository waitingRepository;

	public WaitingGenerateInfo generate(final StoreId storeId) {
		final Store store = getStore(storeId, getUser());
		return WaitingGenerateInfo.builder()
				.store(store)
				.number(new WaitingNumber(getWaitingNumber(store)))
				.turn(new WaitingTurn(getWaitingTurn(store)))
				.build();
	}

	private User getUser() {
		final Username username = SecurityUtils.getAuthenticationUsername();
		return userRepository.findByUsernameOrElseThrow(username);
	}

	private Store getStore(final StoreId storeId, final User user) {
		return storeRepository.findByIdAndUserIdOrElseThrow(storeId, user.getId());
	}

	private int getWaitingNumber(final Store store) {
		return waitingRepository.countByAfterStoreOpen(store.getId(), null, store.getLastOpenedAt()) + 1;
	}

	private int getWaitingTurn(final Store store) {
		return waitingRepository.countByAfterStoreOpen(store.getId(), WaitingStatus.WAIT, store.getLastOpenedAt());
	}
}
