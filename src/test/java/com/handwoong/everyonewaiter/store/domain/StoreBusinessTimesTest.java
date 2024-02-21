package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aStoreBusinessTime;
import static com.handwoong.everyonewaiter.util.Fixtures.aWeekday;
import static com.handwoong.everyonewaiter.util.Fixtures.aWeekend;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.mock.FakeTimeHolder;
import com.handwoong.everyonewaiter.store.infrastructure.StoreBusinessTimeEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class StoreBusinessTimesTest {

	@Test
	void Should_GetListEntity_When_ToEntity() {
		// given
		final StoreBusinessTimes storeBusinessTimes = new StoreBusinessTimes(
				List.of(
						aStoreBusinessTime().daysOfWeek(aWeekday()).build(),
						aStoreBusinessTime()
								.id(new StoreBusinessTimeId(2L))
								.daysOfWeek(aWeekend())
								.build()
				)
		);

		// when
		final List<StoreBusinessTimeEntity> storeBusinessTimeEntities = storeBusinessTimes.toEntity();

		// then
		assertThat(storeBusinessTimeEntities).hasSize(2);
		assertThat(storeBusinessTimeEntities).extracting("id").contains(1L, 2L);
	}

	@Test
	void Should_ThrowException_When_DaysSizeGreaterThanDaysOfWeekSize() {
		// given
		final List<StoreBusinessTime> businessTimes = List.of(
				aStoreBusinessTime().build(),
				aStoreBusinessTime()
						.id(new StoreBusinessTimeId(2L))
						.daysOfWeek(aWeekend())
						.build()
		);

		// expect
		assertThatThrownBy(() -> new StoreBusinessTimes(businessTimes))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("영업 시간의 요일은 " + DayOfWeek.size() + "개까지 선택할 수 있습니다.");
	}

	@Test
	void Should_ThrowException_When_DuplicateDays() {
		// given
		final List<StoreBusinessTime> businessTimes = List.of(
				aStoreBusinessTime().daysOfWeek(aWeekend()).build(),
				aStoreBusinessTime().daysOfWeek(aWeekend()).build()
		);

		// expect
		assertThatThrownBy(() -> new StoreBusinessTimes(businessTimes))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("영업 시간에 중복된 요일이 있습니다.");
	}

	@Test
	void Should_True_When_IsWithinTime() {
		// given
		final FakeTimeHolder timeHolder = new FakeTimeHolder();
		final LocalDateTime mockTime = LocalDateTime.of(2024, 2, 7, 18, 0, 0); // 수요일 18시 0분
		timeHolder.setMillis(mockTime);

		final StoreBusinessTimes storeBusinessTimes = new StoreBusinessTimes(List.of(aStoreBusinessTime().build()));

		// when
		final boolean result = storeBusinessTimes.isWithinBusinessTime(timeHolder);

		// then
		assertThat(result).isTrue();
	}

	@Test
	void Should_False_When_IsWithinTime() {
		// given
		final FakeTimeHolder timeHolder = new FakeTimeHolder();
		final LocalDateTime mockTime = LocalDateTime.of(2024, 2, 7, 22, 0, 0); // 수요일 22시 0분
		timeHolder.setMillis(mockTime);

		final StoreBusinessTimes storeBusinessTimes = new StoreBusinessTimes(List.of(aStoreBusinessTime().build()));

		// when
		final boolean result = storeBusinessTimes.isWithinBusinessTime(timeHolder);

		// then
		assertThat(result).isFalse();
	}
}
