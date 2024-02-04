package com.handwoong.everyonewaiter.store.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class StoreDaysOfWeek {

    private final List<DayOfWeek> daysOfWeek;

    public StoreDaysOfWeek(final List<DayOfWeek> daysOfWeek) {
        validate(daysOfWeek);
        this.daysOfWeek = daysOfWeek;
    }

    private void validate(final List<DayOfWeek> daysOfWeek) {
        final HashSet<DayOfWeek> uniqueDaysOfWeek = new HashSet<>(daysOfWeek);
        if (uniqueDaysOfWeek.size() != daysOfWeek.size()) {
            throw new IllegalArgumentException("요일은 중복될 수 없습니다.");
        }
    }

    public void count(final Map<DayOfWeek, Integer> counter) {
        daysOfWeek.forEach(dayOfWeek -> counter.put(dayOfWeek, counter.get(dayOfWeek) + 1));
    }

    public int getDaysSize() {
        return daysOfWeek.size();
    }

    public String toString(final String delimiter) {
        return daysOfWeek.stream()
            .map(DayOfWeek::toString)
            .collect(Collectors.joining(delimiter));
    }
}
