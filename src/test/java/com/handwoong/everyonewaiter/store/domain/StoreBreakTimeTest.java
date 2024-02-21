package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static com.handwoong.everyonewaiter.util.Fixtures.aStoreBreakTime;
import static com.handwoong.everyonewaiter.util.Fixtures.anAllDay;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StoreBreakTimeTest {

	@Test
	void Should_IncreaseCount_When_MatchedDayOfWeek() {
		// given
		final Map<DayOfWeek, Integer> counter = DayOfWeek.dayOfWeekCounter();
		final StoreBreakTime storeBreakTime = aStoreBreakTime().daysOfWeek(anAllDay()).build();

		// when
		storeBreakTime.daysCount(counter);

		// then
		assertThat(counter)
				.containsEntry(MONDAY, 1)
				.containsEntry(TUESDAY, 1)
				.containsEntry(WEDNESDAY, 1)
				.containsEntry(THURSDAY, 1)
				.containsEntry(FRIDAY, 1)
				.containsEntry(SATURDAY, 1)
				.containsEntry(SUNDAY, 1);
	}

	@Test
	void Should_7_When_GetDaysSize() {
		// given
		final StoreBreakTime storeBreakTime = aStoreBreakTime().daysOfWeek(anAllDay()).build();

		// when
		final int result = storeBreakTime.getDaysSize();

		// then
		assertThat(result).isEqualTo(7);
	}

	@Test
	void Should_True_When_CompareWithinTime() {
		// given
		final StoreBreakTime storeBreakTime = aStoreBreakTime().build();

		// when
		final boolean result = storeBreakTime.compareCurrentTime(MONDAY, LocalTime.of(15, 0, 1));

		// then
		assertThat(result).isTrue();
	}

	@Test
	void Should_False_When_CompareWithinTime() {
		// given
		final StoreBreakTime storeBreakTime = aStoreBreakTime().build();

		// when
		final boolean result = storeBreakTime.compareCurrentTime(MONDAY, LocalTime.of(17, 0, 0));

		// then
		assertThat(result).isFalse();
	}
}
