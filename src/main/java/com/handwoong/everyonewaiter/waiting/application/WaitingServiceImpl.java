package com.handwoong.everyonewaiter.waiting.application;

import com.handwoong.everyonewaiter.common.application.port.UuidHolder;
import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.application.port.UserRepository;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.waiting.application.port.WaitingRepository;
import com.handwoong.everyonewaiter.waiting.controller.port.WaitingService;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingGenerator;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import com.handwoong.everyonewaiter.waiting.domain.WaitingValidator;
import com.handwoong.everyonewaiter.waiting.dto.WaitingCancel;
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WaitingServiceImpl implements WaitingService {

	private final UserRepository userRepository;
	private final StoreRepository storeRepository;
	private final WaitingRepository waitingRepository;
	private final WaitingValidator waitingValidator;
	private final WaitingGenerator waitingGenerator;
	private final UuidHolder uuidHolder;

	@Override
	public int count(final Username username, final StoreId storeId) {
		final User user = userRepository.findByUsernameOrElseThrow(username);
		final Store store = storeRepository.findByIdAndUserIdOrElseThrow(storeId, user.getId());
		return waitingRepository.countByAfterStoreOpen(store.getId(), WaitingStatus.WAIT, store.getLastOpenedAt());
	}

	@Override
	public Waiting findByStoreIdAndUniqueCode(final StoreId storeId, final UUID uniqueCode) {
		return waitingRepository.findByStoreIdAndUniqueCodeOrElseThrow(storeId, uniqueCode);
	}

	@Override
	@Transactional
	public WaitingId register(final WaitingRegister waitingRegister) {
		final Waiting waiting = new Waiting(waitingRegister, waitingValidator, waitingGenerator, uuidHolder);
		final Waiting registeredWaiting = waitingRepository.save(waiting);
		return registeredWaiting.getId();
	}

	@Override
	@Transactional
	public void cancel(final WaitingCancel waitingCancel) {
		final Waiting waiting = waitingRepository.findByStoreIdAndUniqueCodeOrElseThrow(
				waitingCancel.storeId(), waitingCancel.uniqueCode());
		final Waiting canceledWaiting = waiting.cancel();
		waitingRepository.save(canceledWaiting);
	}
}
