package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aStoreOption;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.store.dto.StoreOptionUpdate;
import org.junit.jupiter.api.Test;

class StoreOptionTest {

	@Test
	void Should_UpdateOptionInfo_When_Update() {
		// given
		final StoreOption storeOption = aStoreOption().build();

		final StoreOptionUpdate storeOptionUpdate = StoreOptionUpdate.builder()
				.useBreakTime(false)
				.useWaiting(false)
				.useOrder(false)
				.build();

		// when
		final StoreOption result = storeOption.update(storeOptionUpdate);

		// then
		assertThat(result.isUseBreakTime()).isFalse();
		assertThat(result.isUseWaiting()).isFalse();
		assertThat(result.isUseOrder()).isFalse();
	}
}
