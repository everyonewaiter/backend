package com.handwoong.everyonewaiter.waiting.infrastructure;

import static com.handwoong.everyonewaiter.util.Fixtures.aWaiting;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import org.junit.jupiter.api.Test;

class WaitingEntityTest {

	@Test
	void Should_CreateEntity_When_FromModel() {
		// given
		final Waiting waiting = aWaiting().build();

		// when
		final WaitingEntity waitingEntity = WaitingEntity.from(waiting);

		// then
		assertThat(waitingEntity.getId()).isEqualTo(1L);
		assertThat(waitingEntity.getStoreId()).isEqualTo(1L);
	}

	@Test
	void Should_CreateDomain_When_ToModel() {
		// given
		final Waiting waiting = aWaiting().build();
		final WaitingEntity waitingEntity = WaitingEntity.from(waiting);

		// when
		final Waiting result = waitingEntity.toModel();

		// then
		assertThat(result.getId().value()).isEqualTo(1L);
		assertThat(result.getStoreId().value()).isEqualTo(1L);
	}
}
