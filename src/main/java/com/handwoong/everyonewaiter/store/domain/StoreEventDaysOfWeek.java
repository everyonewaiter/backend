package com.handwoong.everyonewaiter.store.domain;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class StoreEventDaysOfWeek {

    private final List<DayOfWeek> daysOfWeek;

    public StoreEventDaysOfWeek(final List<DayOfWeek> daysOfWeek) {
        validate(daysOfWeek);
        this.daysOfWeek = daysOfWeek;
    }

    private void validate(final List<DayOfWeek> daysOfWeek) {
        final HashSet<DayOfWeek> uniqueDaysOfWeek = new HashSet<>(daysOfWeek);
        if (uniqueDaysOfWeek.size() != daysOfWeek.size()) {
            throw new IllegalArgumentException("요일은 중복될 수 없습니다.");
        }
    }

    public String toString(final String delimiter) {
        return daysOfWeek.stream()
            .map(DayOfWeek::toString)
            .collect(Collectors.joining(delimiter));
    }
}
