package com.handwoong.everyonewaiter.store.domain;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoreEventDaysOfWeek {

    private final List<DayOfWeek> daysOfWeek;

    public String toString(final String delimiter) {
        return daysOfWeek.stream()
            .map(DayOfWeek::toString)
            .collect(Collectors.joining(delimiter));
    }
}
