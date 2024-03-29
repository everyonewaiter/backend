package com.handwoong.everyonewaiter.store.application;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.store.domain.StoreOption;
import com.handwoong.everyonewaiter.store.dto.StoreCreate;
import com.handwoong.everyonewaiter.store.dto.StoreOptionUpdate;
import com.handwoong.everyonewaiter.store.dto.StoreUpdate;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoreServiceImplTest {

	private TestContainer testContainer;

	@BeforeEach
	void setUp() {
		testContainer = new TestContainer();

		final User user = aUser().build();
		testContainer.userRepository.save(user);

		final Store store = aStore().build();
		testContainer.storeRepository.save(store);
	}

	@Test
	void Should_FindStores_When_ValidUsername() {
		// given
		final Username username = new Username("handwoong");

		// when
		final List<Store> stores = testContainer.storeService.findAllByUsername(username);

		// then
		assertThat(stores).hasSize(1);
	}

	@Test
	void Should_ThrowException_When_FindStoresUsernameNotFound() {
		// given
		final Username username = new Username("username");

		// expect
		assertThatThrownBy(() -> testContainer.storeService.findAllByUsername(username))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_FindStore_When_ValidStoreIdAndUsername() {
		final StoreId storeId = new StoreId(1L);
		final Username username = new Username("handwoong");

		// when
		final Store store = testContainer.storeService.findByIdAndUsername(storeId, username);

		// then
		assertThat(store.getId()).isEqualTo(storeId);
	}

	@Test
	void Should_ThrowException_When_FindStoreUsernameNotFound() {
		// given
		final StoreId storeId = new StoreId(1L);
		final Username username = new Username("username");

		// expect
		assertThatThrownBy(() -> testContainer.storeService.findByIdAndUsername(storeId, username))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_FindStoreStoreNotFound() {
		// given
		final StoreId storeId = new StoreId(2L);
		final Username username = new Username("handwoong");

		// expect
		assertThatThrownBy(() -> testContainer.storeService.findByIdAndUsername(storeId, username))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_Create_When_ValidStoreCreate() {
		// given
		final StoreCreate storeCreate = StoreCreate.builder()
				.name(new StoreName("나루"))
				.landlineNumber(new LandlineNumber("0551234567"))
				.businessTimes(new StoreBusinessTimes(List.of()))
				.breakTimes(new StoreBreakTimes(List.of()))
				.option(
						StoreOption.builder()
								.useBreakTime(true)
								.useWaiting(true)
								.useOrder(true)
								.build()
				)
				.build();

		// when
		final StoreId storeId = testContainer.storeService.create(new Username("handwoong"), storeCreate);

		// then
		assertThat(storeId).isEqualTo(new StoreId(1L));
	}

	@Test
	void Should_ThrowException_When_CreateUserNotFound() {
		// given
		final Username username = new Username("username");
		final StoreCreate storeCreate = StoreCreate.builder().build();

		// expect
		assertThatThrownBy(() -> testContainer.storeService.create(username, storeCreate))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_Update_When_ValidStoreUpdate() {
		// given
		final Username username = new Username("handwoong");

		final StoreUpdate storeUpdate = StoreUpdate.builder()
				.id(new StoreId(1L))
				.name(new StoreName("나루 레스토랑"))
				.landlineNumber(new LandlineNumber("0551234567"))
				.businessTimes(new StoreBusinessTimes(List.of()))
				.breakTimes(new StoreBreakTimes(List.of()))
				.build();

		// when
		testContainer.storeService.update(username, storeUpdate);
		final Store result =
				testContainer.storeRepository.findByIdAndUserIdOrElseThrow(new StoreId(1L), new UserId(1L));

		// then
		assertThat(result.getName()).hasToString("나루 레스토랑");
	}

	@Test
	void Should_ThrowException_When_UpdateUserNotFound() {
		// given
		final Username username = new Username("username");
		final StoreUpdate storeUpdate = StoreUpdate.builder().build();

		// expect
		assertThatThrownBy(() -> testContainer.storeService.update(username, storeUpdate))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_UpdateStoreNotFound() {
		// given
		final Username username = new Username("handwoong");
		final StoreUpdate storeUpdate = StoreUpdate.builder().id(new StoreId(10L)).build();

		// expect
		assertThatThrownBy(() -> testContainer.storeService.update(username, storeUpdate))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_UpdateOption_When_ValidStoreUpdateOption() {
		// given
		final Username username = new Username("handwoong");

		final StoreOptionUpdate storeOptionUpdate = StoreOptionUpdate.builder()
				.storeId(new StoreId(1L))
				.useBreakTime(false)
				.useWaiting(false)
				.useOrder(false)
				.build();

		// when
		testContainer.storeService.update(username, storeOptionUpdate);
		final Store result =
				testContainer.storeRepository.findByIdAndUserIdOrElseThrow(new StoreId(1L), new UserId(1L));

		// then
		assertThat(result.getOption().isUseBreakTime()).isFalse();
		assertThat(result.getOption().isUseWaiting()).isFalse();
		assertThat(result.getOption().isUseOrder()).isFalse();
	}

	@Test
	void Should_ThrowException_When_UpdateOptionUserNotFound() {
		// given
		final Username username = new Username("username");
		final StoreOptionUpdate storeOptionUpdate = StoreOptionUpdate.builder().build();

		// expect
		assertThatThrownBy(() -> testContainer.storeService.update(username, storeOptionUpdate))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_UpdateOptionStoreNotFound() {
		// given
		final Username username = new Username("handwoong");
		final StoreOptionUpdate storeOptionUpdate = StoreOptionUpdate.builder().storeId(new StoreId(10L)).build();

		// expect
		assertThatThrownBy(() -> testContainer.storeService.update(username, storeOptionUpdate))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_DeleteStore_When_StoreId() {
		// given
		final Username username = new Username("handwoong");
		final StoreId storeId = new StoreId(1L);
		final UserId userId = new UserId(1L);

		// when
		testContainer.storeService.delete(username, storeId);

		// then
		assertThatThrownBy(() -> testContainer.storeRepository.findByIdAndUserIdOrElseThrow(storeId, userId))
				.isInstanceOf(StoreNotFoundException.class);
	}
}
