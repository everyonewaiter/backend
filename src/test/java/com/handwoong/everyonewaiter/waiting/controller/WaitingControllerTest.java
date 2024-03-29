package com.handwoong.everyonewaiter.waiting.controller;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aStoreOption;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static com.handwoong.everyonewaiter.util.Fixtures.aWaiting;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreStatus;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import com.handwoong.everyonewaiter.waiting.controller.request.WaitingCancelRequest;
import com.handwoong.everyonewaiter.waiting.controller.request.WaitingRegisterRequest;
import com.handwoong.everyonewaiter.waiting.controller.response.WaitingCountResponse;
import com.handwoong.everyonewaiter.waiting.controller.response.WaitingResponse;
import com.handwoong.everyonewaiter.waiting.controller.response.WaitingTurnResponse;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import com.handwoong.everyonewaiter.waiting.exception.WaitingNotFoundException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class WaitingControllerTest {

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

		final Waiting waiting = aWaiting().phoneNumber(new PhoneNumber("01011112222")).build();
		testContainer.waitingRepository.save(waiting);
	}

	@Test
	void Should_1_When_Count() {
		// given
		// when
		final ResponseEntity<ApiResponse<WaitingCountResponse>> response = testContainer.waitingController.count(1L);
		final ApiResponse<WaitingCountResponse> result = Objects.requireNonNull(response.getBody());

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(result.data().count()).isEqualTo(1);
	}

	@Test
	void Should_Find_When_StoreIdAndUniqueCode() {
		// given
		final UUID uniqueCode = testContainer.uuidHolder.generate();

		// when
		final ResponseEntity<ApiResponse<WaitingResponse>> response =
				testContainer.waitingController.findByStoreIdAndUniqueCode(1L, uniqueCode);
		final ApiResponse<WaitingResponse> result = Objects.requireNonNull(response.getBody());

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(result.data().id()).isEqualTo(1L);
	}

	@Test
	void Should_2_When_Turn() {
		// given
		for (long i = 1; i <= 2; i++) {
			final Waiting waiting = aWaiting().id(new WaitingId(i)).build();
			testContainer.waitingRepository.save(waiting);
		}

		final UUID uniqueCode = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
		final Waiting waiting = aWaiting().id(new WaitingId(3L)).uniqueCode(uniqueCode).build();
		testContainer.waitingRepository.save(waiting);

		// when
		final ResponseEntity<ApiResponse<WaitingTurnResponse>> response =
				testContainer.waitingController.turn(1L, uniqueCode);
		final ApiResponse<WaitingTurnResponse> result = Objects.requireNonNull(response.getBody());

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(result.data().turn()).isEqualTo(2);
	}

	@Test
	void Should_Minus1_When_TurnStatusNotWait() {
		// given
		final UUID uniqueCode = testContainer.uuidHolder.generate();
		final Waiting waiting = aWaiting().status(WaitingStatus.CANCEL).build();
		testContainer.waitingRepository.save(waiting);

		// when
		final ResponseEntity<ApiResponse<WaitingTurnResponse>> response =
				testContainer.waitingController.turn(1L, uniqueCode);
		final ApiResponse<WaitingTurnResponse> result = Objects.requireNonNull(response.getBody());

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(result.data().turn()).isEqualTo(-1);
	}

	@Test
	void Should_Register_When_ValidRequest() {
		// given
		final WaitingRegisterRequest request = new WaitingRegisterRequest(1L, 2, 0, "01012345678");

		// when
		final ResponseEntity<ApiResponse<Void>> response = testContainer.waitingController.register(request);

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(201);
	}

	@Test
	void Should_ThrowException_When_UnUseWaiting() {
		// given
		final Store store = aStore().id(new StoreId(2L)).option(aStoreOption().useWaiting(false).build()).build();
		testContainer.storeRepository.save(store);

		final WaitingRegisterRequest request = new WaitingRegisterRequest(2L, 2, 0, "01012345678");

		// expect
		assertThatThrownBy(() -> testContainer.waitingController.register(request))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("웨이팅 기능을 사용하지 않는 매장입니다.");
	}

	@Test
	void Should_ThrowException_When_CloseStore() {
		// given
		final Store store = aStore().id(new StoreId(2L)).status(StoreStatus.CLOSE).build();
		testContainer.storeRepository.save(store);

		final WaitingRegisterRequest request = new WaitingRegisterRequest(2L, 2, 0, "01012345678");

		// expect
		assertThatThrownBy(() -> testContainer.waitingController.register(request))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("매장이 영업중이 아닙니다.");
	}

	@Test
	void Should_ThrowException_When_OverBusinessTime() {
		// given
		testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 22, 0, 0)); // 월요일 22시 0분
		final WaitingRegisterRequest request = new WaitingRegisterRequest(1L, 2, 0, "01012345678");

		// expect
		assertThatThrownBy(() -> testContainer.waitingController.register(request))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("매장 영업 시간이 아닙니다.");
	}

	@Test
	void Should_ThrowException_When_WithinBreakTime() {
		// given
		testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 16, 0, 0)); // 월요일 16시 0분
		final WaitingRegisterRequest request = new WaitingRegisterRequest(1L, 2, 0, "01012345678");

		// expect
		assertThatThrownBy(() -> testContainer.waitingController.register(request))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("브레이크 타임에는 웨이팅을 등록할 수 없습니다.");
	}

	@Test
	void Should_Cancel_When_ValidRequest() {
		// given
		final WaitingCancelRequest request =
				new WaitingCancelRequest(1L, UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

		// when
		final ResponseEntity<ApiResponse<Void>> response = testContainer.waitingController.cancel(request);

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	void Should_ThrowException_When_CancelStoreIdNotFound() {
		// given
		final WaitingCancelRequest request =
				new WaitingCancelRequest(2L, UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

		// expect
		assertThatThrownBy(() -> testContainer.waitingController.cancel(request))
				.isInstanceOf(WaitingNotFoundException.class)
				.hasMessage("웨이팅을 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_CancelUniqueCodeNotFound() {
		// given
		final WaitingCancelRequest request =
				new WaitingCancelRequest(1L, UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"));

		// expect
		assertThatThrownBy(() -> testContainer.waitingController.cancel(request))
				.isInstanceOf(WaitingNotFoundException.class)
				.hasMessage("웨이팅을 찾을 수 없습니다.");
	}
}
