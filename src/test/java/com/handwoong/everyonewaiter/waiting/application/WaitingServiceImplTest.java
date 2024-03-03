package com.handwoong.everyonewaiter.waiting.application;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static com.handwoong.everyonewaiter.util.Fixtures.aWaiting;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingAdult;
import com.handwoong.everyonewaiter.waiting.domain.WaitingChildren;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNotificationType;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import com.handwoong.everyonewaiter.waiting.dto.WaitingCancel;
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;
import com.handwoong.everyonewaiter.waiting.exception.AlreadyExistsPhoneNumberException;
import com.handwoong.everyonewaiter.waiting.exception.WaitingNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WaitingServiceImplTest {

	private TestContainer testContainer;

	@BeforeEach
	void setUp() {
		final Username username = new Username("handwoong");
		testContainer = new TestContainer();
		testContainer.setSecurityContextAuthentication(username);
		testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 18, 0, 0)); // 월요일 18시 0분

		final User user = aUser().build();
		testContainer.userRepository.save(user);

		final Store store = aStore().build();
		testContainer.storeRepository.save(store);

		final Waiting waiting = aWaiting().build();
		testContainer.waitingRepository.save(waiting);
	}

	@Test
	void Should_1_When_Count() {
		// given
		final Username username = new Username("handwoong");
		final StoreId storeId = new StoreId(1L);

		// when
		final int result = testContainer.waitingService.count(username, storeId);

		// then
		assertThat(result).isEqualTo(1);
	}

	@Test
	void Should_Zero_When_Count() {
		// given
		final Waiting waiting = aWaiting().status(WaitingStatus.CANCEL).build();
		testContainer.waitingRepository.save(waiting);

		final Username username = new Username("handwoong");
		final StoreId storeId = new StoreId(1L);

		// when
		final int result = testContainer.waitingService.count(username, storeId);

		// then
		assertThat(result).isZero();
	}

	@Test
	void Should_Find_When_StoreIdAndUniqueCode() {
		// given
		final StoreId storeId = new StoreId(1L);
		final UUID uniqueCode = testContainer.uuidHolder.generate();

		// when
		final Waiting waiting = testContainer.waitingService.findByStoreIdAndUniqueCode(storeId, uniqueCode);

		// then
		assertThat(waiting.getId().value()).isEqualTo(1L);
	}

	@Test
	void Should_2_When_Turn() {
		// given
		final StoreId storeId = new StoreId(1L);
		for (long i = 1; i <= 2; i++) {
			final Waiting waiting = aWaiting().id(new WaitingId(i)).build();
			testContainer.waitingRepository.save(waiting);
		}

		final UUID uniqueCode = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
		final Waiting waiting = aWaiting().id(new WaitingId(3L)).uniqueCode(uniqueCode).build();
		testContainer.waitingRepository.save(waiting);

		// when
		final int result = testContainer.waitingService.turn(storeId, uniqueCode);

		// then
		assertThat(result).isEqualTo(2);
	}

	@Test
	void Should_Minus1_When_TurnStatusNotWait() {
		// given
		final StoreId storeId = new StoreId(1L);
		final UUID uniqueCode = testContainer.uuidHolder.generate();

		final Waiting waiting = aWaiting().status(WaitingStatus.CANCEL).build();
		testContainer.waitingRepository.save(waiting);

		// when
		final int result = testContainer.waitingService.turn(storeId, uniqueCode);

		// then
		assertThat(result).isEqualTo(-1);
	}

	@Test
	void Should_Register_When_ValidWaitingRegister() {
		// given
		final WaitingRegister waitingRegister = WaitingRegister.builder()
				.storeId(new StoreId(1L))
				.adult(new WaitingAdult(2))
				.children(new WaitingChildren(0))
				.phoneNumber(new PhoneNumber("01011112222"))
				.build();

		// when
		final WaitingId waitingId = testContainer.waitingService.register(waitingRegister);

		// then
		assertThat(waitingId.value()).isEqualTo(1L);
	}

	@Test
	void Should_ThrowException_When_DuplicatePhoneNumber() {
		// given
		final PhoneNumber phoneNumber = new PhoneNumber("01012345678");
		final WaitingRegister waitingRegister = WaitingRegister.builder()
				.phoneNumber(phoneNumber)
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.waitingService.register(waitingRegister))
				.isInstanceOf(AlreadyExistsPhoneNumberException.class)
				.hasMessage("이미 웨이팅에 등록되어 있는 휴대폰 번호입니다.");
	}

	@Test
	void Should_Cancel_When_ValidWaitingCancel() {
		// given
		final StoreId storeId = new StoreId(1L);
		final UUID uniqueCode = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
		final WaitingCancel waitingCancel = WaitingCancel.builder()
				.storeId(storeId)
				.uniqueCode(uniqueCode)
				.build();

		// when
		testContainer.waitingService.cancel(waitingCancel);
		final Waiting result =
				testContainer.waitingRepository.findByStoreIdAndUniqueCodeOrElseThrow(storeId, uniqueCode);

		// then
		assertThat(result.getStatus()).isEqualTo(WaitingStatus.CANCEL);
		assertThat(result.getNotificationType()).isEqualTo(WaitingNotificationType.CANCEL);
	}

	@Test
	void Should_ThrowException_When_CancelStoreIdNotFound() {
		// given
		final StoreId storeId = new StoreId(2L);
		final UUID uniqueCode = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
		final WaitingCancel waitingCancel = WaitingCancel.builder()
				.storeId(storeId)
				.uniqueCode(uniqueCode)
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.waitingService.cancel(waitingCancel))
				.isInstanceOf(WaitingNotFoundException.class)
				.hasMessage("웨이팅을 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_CancelUniqueCodeNotFound() {
		// given
		final StoreId storeId = new StoreId(1L);
		final UUID uniqueCode = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
		final WaitingCancel waitingCancel = WaitingCancel.builder()
				.storeId(storeId)
				.uniqueCode(uniqueCode)
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.waitingService.cancel(waitingCancel))
				.isInstanceOf(WaitingNotFoundException.class)
				.hasMessage("웨이팅을 찾을 수 없습니다.");
	}
}
