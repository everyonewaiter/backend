package com.handwoong.everyonewaiter.waiting.application;

import com.handwoong.everyonewaiter.common.application.port.UuidHolder;
import com.handwoong.everyonewaiter.waiting.application.port.WaitingRepository;
import com.handwoong.everyonewaiter.waiting.controller.port.WaitingService;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingGenerator;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingValidator;
import com.handwoong.everyonewaiter.waiting.dto.WaitingCancel;
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WaitingServiceImpl implements WaitingService {

	private final WaitingRepository waitingRepository;
	private final WaitingValidator waitingValidator;
	private final WaitingGenerator waitingGenerator;
	private final UuidHolder uuidHolder;

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
