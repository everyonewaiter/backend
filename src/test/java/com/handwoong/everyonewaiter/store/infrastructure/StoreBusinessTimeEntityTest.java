package com.handwoong.everyonewaiter.store.infrastructure;

import static com.handwoong.everyonewaiter.util.Fixtures.aStoreBusinessTime;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.store.domain.StoreBusinessTime;
import org.junit.jupiter.api.Test;

class StoreBusinessTimeEntityTest {

	@Test
	void Should_CreateEntity_When_FromModel() {
		// given
		final StoreBusinessTime storeBusinessTime = aStoreBusinessTime().build();

		// when
		final StoreBusinessTimeEntity storeBusinessTimeEntity = StoreBusinessTimeEntity.from(storeBusinessTime);

		// then
		assertThat(storeBusinessTimeEntity).extracting("id").isEqualTo(1L);
	}

	@Test
	void Should_CreateDomain_When_ToModel() {
		// given
		final StoreBusinessTime storeBusinessTime = aStoreBusinessTime().build();
		final StoreBusinessTimeEntity storeBusinessTimeEntity = StoreBusinessTimeEntity.from(storeBusinessTime);

		// when
		final StoreBusinessTime result = storeBusinessTimeEntity.toModel();

		// then
		assertThat(result.getId().value()).isEqualTo(1L);
	}
}
