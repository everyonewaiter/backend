package com.handwoong.everyonewaiter.store.infrastructure;

import com.handwoong.everyonewaiter.store.domain.DayOfWeek;
import com.handwoong.everyonewaiter.store.domain.StoreDaysOfWeek;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

@Converter
public class DaysOfWeekConverter implements AttributeConverter<StoreDaysOfWeek, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(final StoreDaysOfWeek daysOfWeek) {
        return daysOfWeek.toString(DELIMITER);
    }

    @Override
    public StoreDaysOfWeek convertToEntityAttribute(final String s) {
        return new StoreDaysOfWeek(
            Arrays.stream(s.split(DELIMITER))
                .map(DayOfWeek::from)
                .toList()
        );
    }
}
