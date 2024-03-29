package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.common.application.port.TimeHolder;
import com.handwoong.everyonewaiter.store.infrastructure.StoreBusinessTimeEntity;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public record StoreBusinessTimes(List<StoreBusinessTime> businessTimes) {

	public StoreBusinessTimes {
		validate(businessTimes);
	}

	private void validate(final List<StoreBusinessTime> businessTimes) {
		if (getTotalDays(businessTimes) > DayOfWeek.size()) {
			throw new IllegalArgumentException("영업 시간의 요일은 " + DayOfWeek.size() + "개까지 선택할 수 있습니다.");
		}
		if (hasDuplicateDays(businessTimes)) {
			throw new IllegalArgumentException("영업 시간에 중복된 요일이 있습니다.");
		}
	}

	private int getTotalDays(final List<StoreBusinessTime> businessTimes) {
		return businessTimes.stream()
				.map(StoreBusinessTime::getDaysSize)
				.reduce(0, Integer::sum);
	}

	private boolean hasDuplicateDays(final List<StoreBusinessTime> businessTimes) {
		final Map<DayOfWeek, Integer> counter = DayOfWeek.dayOfWeekCounter();
		businessTimes.forEach(businessTime -> businessTime.daysCount(counter));
		return counter.values().stream().anyMatch(value -> value > 1);
	}

	public boolean isWithinBusinessTime(final TimeHolder timeHolder) {
		final long millis = timeHolder.millis();
		final DayOfWeek dayOfWeek = getDayOfWeek(millis);
		final LocalTime currentTime = getLocalTime(millis);
		return businessTimes.stream()
				.anyMatch(businessTime -> businessTime.compareCurrentTime(dayOfWeek, currentTime));
	}

	private DayOfWeek getDayOfWeek(final long millis) {
		final SimpleDateFormat dayOfWeekFormatter = new SimpleDateFormat("E");
		final String dayOfWeek = dayOfWeekFormatter.format(new Date(millis));
		return DayOfWeek.from(dayOfWeek);
	}

	private LocalTime getLocalTime(final long millis) {
		final Instant instant = Instant.ofEpochMilli(millis);
		final ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		return zdt.toLocalTime();
	}

	public List<StoreBusinessTimeEntity> toEntity() {
		return businessTimes.stream()
				.map(StoreBusinessTimeEntity::from)
				.toList();
	}

	@Override
	public List<StoreBusinessTime> businessTimes() {
		return Collections.unmodifiableList(businessTimes);
	}
}
