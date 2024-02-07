package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.common.application.port.TimeHolder;
import com.handwoong.everyonewaiter.store.infrastructure.StoreBreakTimeEntity;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public record StoreBreakTimes(List<StoreBreakTime> breakTimes) {

    public StoreBreakTimes {
        validate(breakTimes);
    }

    private void validate(final List<StoreBreakTime> breakTimes) {
        if (getTotalDays(breakTimes) > DayOfWeek.size()) {
            throw new IllegalArgumentException("브레이크 타임의 요일은 총 " + DayOfWeek.size() + "개까지 선택할 수 있습니다.");
        }
        if (hasDuplicateDays(breakTimes)) {
            throw new IllegalArgumentException("브레이크 타임에 중복된 요일이 있습니다.");
        }
    }

    private int getTotalDays(final List<StoreBreakTime> breakTimes) {
        return breakTimes.stream()
            .map(StoreBreakTime::getDaysSize)
            .reduce(0, Integer::sum);
    }

    private boolean hasDuplicateDays(final List<StoreBreakTime> breakTimes) {
        final Map<DayOfWeek, Integer> counter = DayOfWeek.dayOfWeekCounter();
        breakTimes.forEach(breakTime -> breakTime.daysCount(counter));
        return counter.values().stream().anyMatch(value -> value > 1);
    }

    public boolean isWithinBreakTime(final TimeHolder timeHolder) {
        final long millis = timeHolder.millis();
        final DayOfWeek dayOfWeek = getDayOfWeek(millis);
        final LocalTime currentTime = getLocalTime(millis);
        return breakTimes.stream()
            .anyMatch(breakTime -> breakTime.compareCurrentTime(dayOfWeek, currentTime));
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

    public List<StoreBreakTimeEntity> toEntity() {
        return breakTimes.stream()
            .map(StoreBreakTimeEntity::from)
            .toList();
    }

    @Override
    public List<StoreBreakTime> breakTimes() {
        return Collections.unmodifiableList(breakTimes);
    }
}
