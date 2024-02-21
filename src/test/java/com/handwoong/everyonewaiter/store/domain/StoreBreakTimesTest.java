package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aStoreBreakTime;
import static com.handwoong.everyonewaiter.util.Fixtures.aWeekend;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.mock.FakeTimeHolder;
import com.handwoong.everyonewaiter.store.infrastructure.StoreBreakTimeEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class StoreBreakTimesTest {

	@Test
	void Should_GetListEntity_When_ToEntity() {
		// given
		final StoreBreakTimes storeBreakTimes = new StoreBreakTimes(
				List.of(
						aStoreBreakTime().build(),
						aStoreBreakTime().id(new StoreBreakTimeId(2L)).daysOfWeek(aWeekend()).build())
		);

		// when
		final List<StoreBreakTimeEntity> storeBreakTimeEntities = storeBreakTimes.toEntity();

		// then
		assertThat(storeBreakTimeEntities).hasSize(2);
		assertThat(storeBreakTimeEntities).extracting("id").contains(1L, 2L);
	}

	@Test
	void Should_ThrowException_When_DaysSizeGreaterThanDaysOfWeekSize() {
		// given
		final List<StoreBreakTime> breakTimes = List.of(
				aStoreBreakTime().build(),
				aStoreBreakTime().build()
		);

		// expect
		assertThatThrownBy(() -> new StoreBreakTimes(breakTimes))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("브레이크 타임의 요일은 총 " + DayOfWeek.size() + "개까지 선택할 수 있습니다.");
	}

	@Test
	void Should_ThrowException_When_DuplicateDays() {
		// given
		final List<StoreBreakTime> breakTimes = List.of(
				aStoreBreakTime().daysOfWeek(aWeekend()).build(),
				aStoreBreakTime().daysOfWeek(aWeekend()).build()
		);

		// expect
		assertThatThrownBy(() -> new StoreBreakTimes(breakTimes))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("브레이크 타임에 중복된 요일이 있습니다.");
	}

	@Test
	void Should_True_When_IsWithinTime() {
		// given
		final FakeTimeHolder timeHolder = new FakeTimeHolder();
		final LocalDateTime mockTime = LocalDateTime.of(2024, 2, 7, 15, 50, 0); // 수요일 15시 50분
		timeHolder.setMillis(mockTime);

		final StoreBreakTimes storeBreakTimes = new StoreBreakTimes(List.of(aStoreBreakTime().build()));

		// when
		final boolean result = storeBreakTimes.isWithinBreakTime(timeHolder);

		// then
		assertThat(result).isTrue();
	}

	@Test
	void Should_False_When_IsWithinTime() {
		// given
		final FakeTimeHolder timeHolder = new FakeTimeHolder();
		final LocalDateTime mockTime = LocalDateTime.of(2024, 2, 7, 16, 40, 0); // 수요일 16시 40분
		timeHolder.setMillis(mockTime);

		final StoreBreakTimes storeBreakTimes = new StoreBreakTimes(List.of(aStoreBreakTime().build()));

		// when
		final boolean result = storeBreakTimes.isWithinBreakTime(timeHolder);

		// then
		assertThat(result).isFalse();
	}
}
