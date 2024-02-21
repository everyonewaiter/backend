package com.handwoong.everyonewaiter.store.infrastructure;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.store.domain.Store;
import org.junit.jupiter.api.Test;

class StoreEntityTest {

	@Test
	void Should_CreateEntity_When_FromModel() {
		// given
		final Store store = aStore().build();

		// when
		final StoreEntity storeEntity = StoreEntity.from(store);

		// then
		assertThat(storeEntity).extracting("id").isEqualTo(1L);
		assertThat(storeEntity.getBusinessTimeEntities()).extracting("id").contains(1L);
		assertThat(storeEntity.getBreakTimeEntities()).extracting("id").contains(1L, 2L);
		assertThat(storeEntity.getOptionEntity()).extracting("id").isEqualTo(1L);
	}

	@Test
	void Should_CreateDomain_When_ToModel() {
		// given
		final Store store = aStore().build();
		final StoreEntity storeEntity = StoreEntity.from(store);

		// when
		final Store result = storeEntity.toModel();

		// then
		assertThat(result.getId().value()).isEqualTo(1L);
	}

	@Test
	void Should_ThrowException_When_UserIdIsNull() {
		// given
		final Store store = aStore().userId(null).build();

		// expect
		assertThatThrownBy(() -> StoreEntity.from(store))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("매장의 사용자 ID는 null일 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_FromModelStoreNameIsNull() {
		// given
		final Store store = aStore().name(null).build();

		// expect
		assertThatThrownBy(() -> StoreEntity.from(store))
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	void Should_ThrowException_When_FromModelLandlineNumberIsNull() {
		// given
		final Store store = aStore().landlineNumber(null).build();

		// expect
		assertThatThrownBy(() -> StoreEntity.from(store))
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	void Should_ThrowException_When_FromModelBusinessTimesIsNull() {
		// given
		final Store store = aStore().businessTimes(null).build();

		// expect
		assertThatThrownBy(() -> StoreEntity.from(store))
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	void Should_ThrowException_When_FromModelBreakTimesIsNull() {
		// given
		final Store store = aStore().breakTimes(null).build();

		// expect
		assertThatThrownBy(() -> StoreEntity.from(store))
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	void Should_ThrowException_When_FromModelStoreOptionIsNull() {
		// given
		final Store store = aStore().option(null).build();

		// expect
		assertThatThrownBy(() -> StoreEntity.from(store))
				.isInstanceOf(NullPointerException.class);
	}
}
