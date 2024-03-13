package com.handwoong.everyonewaiter.waiting.domain;

import static com.handwoong.everyonewaiter.waiting.domain.WaitingStatus.CANCEL;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingStatus.WAIT;

import com.handwoong.everyonewaiter.common.application.port.UuidHolder;
import com.handwoong.everyonewaiter.common.domain.AggregateRoot;
import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.domain.event.WaitingCancelEvent;
import com.handwoong.everyonewaiter.waiting.domain.event.WaitingRegisterEvent;
import com.handwoong.everyonewaiter.waiting.dto.WaitingGenerateInfo;
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Waiting extends AggregateRoot {

	private final WaitingId id;
	private final StoreId storeId;
	private final WaitingAdult adult;
	private final WaitingChildren children;
	private final WaitingNumber number;
	private final PhoneNumber phoneNumber;
	private final WaitingStatus status;
	private final WaitingNotificationType notificationType;
	private final UUID uniqueCode;
	private final DomainTimestamp timestamp;

	public Waiting(
			final WaitingRegister waitingRegister,
			final WaitingValidator waitingValidator,
			final WaitingGenerator waitingGenerator,
			final UuidHolder uuidHolder
	) {
		waitingValidator.validate(waitingRegister.storeId(), waitingRegister.phoneNumber());
		final WaitingGenerateInfo waitingInfo = waitingGenerator.generate(waitingRegister.storeId());
		final UUID generatedUniqueCode = uuidHolder.generate();

		this.id = null;
		this.storeId = waitingRegister.storeId();
		this.adult = waitingRegister.adult();
		this.children = waitingRegister.children();
		this.number = waitingInfo.number();
		this.phoneNumber = waitingRegister.phoneNumber();
		this.status = WAIT;
		this.notificationType = WaitingNotificationType.REGISTER;
		this.uniqueCode = generatedUniqueCode;
		this.timestamp = null;

		registerEvent(
				new WaitingRegisterEvent(
						waitingInfo,
						waitingRegister.adult(),
						waitingRegister.children(),
						generatedUniqueCode,
						waitingRegister.phoneNumber()
				)
		);
	}

	public Waiting cancel() {
		registerEvent(new WaitingCancelEvent(storeId, number, phoneNumber));
		return Waiting.builder()
				.id(id)
				.storeId(storeId)
				.adult(adult)
				.children(children)
				.number(number)
				.phoneNumber(phoneNumber)
				.status(CANCEL)
				.notificationType(WaitingNotificationType.CANCEL)
				.uniqueCode(uniqueCode)
				.timestamp(timestamp)
				.build();
	}

	public boolean isNotWait() {
		return status != WAIT;
	}
}
