package com.handwoong.everyonewaiter.store.infrastructure;

import static com.handwoong.everyonewaiter.util.Fixtures.aStoreOption;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.store.domain.StoreOption;
import org.junit.jupiter.api.Test;

class StoreOptionEntityTest {

	@Test
	void Should_CreateEntity_When_FromModel() {
		// given
		final StoreOption storeOption = aStoreOption().build();

		// when
		final StoreOptionEntity storeOptionEntity = StoreOptionEntity.from(storeOption);

		// then
		assertThat(storeOptionEntity).extracting("id").isEqualTo(1L);
	}

	@Test
	void Should_CreateDomain_When_ToModel() {
		// given
		final StoreOption storeOption = aStoreOption().build();
		final StoreOptionEntity storeOptionEntity = StoreOptionEntity.from(storeOption);

		// when
		final StoreOption result = storeOptionEntity.toModel();

		// then
		assertThat(result.getId().value()).isEqualTo(1L);
	}
}
