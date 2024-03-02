package com.handwoong.everyonewaiter.waiting.domain;

import com.handwoong.everyonewaiter.common.application.port.TimeHolder;
import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.application.port.UserRepository;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.infrastructure.SecurityUtils;
import com.handwoong.everyonewaiter.waiting.application.port.WaitingRepository;
import com.handwoong.everyonewaiter.waiting.exception.AlreadyExistsPhoneNumberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingValidator {

	private final UserRepository userRepository;
	private final StoreRepository storeRepository;
	private final WaitingRepository waitingRepository;
	private final TimeHolder timeHolder;

	public void validate(final StoreId storeId, final PhoneNumber phoneNumber) {
		validate(phoneNumber);
		validate(getStore(storeId, getUser()));
	}

	private void validate(final PhoneNumber phoneNumber) {
		if (waitingRepository.existsByPhoneNumber(phoneNumber)) {
			throw new AlreadyExistsPhoneNumberException("이미 웨이팅에 등록되어 있는 휴대폰 번호입니다.", phoneNumber.toString());
		}
	}

	private void validate(final Store store) {
		if (!store.isUseWaiting()) {
			throw new IllegalArgumentException("웨이팅 기능을 사용하지 않는 매장입니다.");
		}
		if (!store.isOpen()) {
			throw new IllegalArgumentException("매장이 영업중이 아닙니다.");
		}
		if (!store.isWithinBusinessTime(timeHolder)) {
			throw new IllegalArgumentException("매장 영업 시간이 아닙니다.");
		}
		if (store.isUseBreakTime() && store.isWithinBreakTime(timeHolder)) {
			throw new IllegalArgumentException("브레이크 타임에는 웨이팅을 등록할 수 없습니다.");
		}
	}

	private User getUser() {
		final Username username = SecurityUtils.getAuthenticationUsername();
		return userRepository.findByUsernameOrElseThrow(username);
	}

	private Store getStore(final StoreId storeId, final User user) {
		return storeRepository.findByIdAndUserIdOrElseThrow(storeId, user.getId());
	}
}
