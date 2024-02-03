package com.handwoong.everyonewaiter.store.infrastructure;

import com.handwoong.everyonewaiter.store.domain.DayOfWeek;
import com.handwoong.everyonewaiter.store.domain.StoreEventDaysOfWeek;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

@Converter
public class DaysOfWeekConverter implements AttributeConverter<StoreEventDaysOfWeek, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(final StoreEventDaysOfWeek daysOfWeek) {
        return daysOfWeek.toString(DELIMITER);
    }

    @Override
    public StoreEventDaysOfWeek convertToEntityAttribute(final String s) {
        return new StoreEventDaysOfWeek(
            Arrays.stream(s.split(DELIMITER))
                .map(DayOfWeek::from)
                .toList()
        );
    }
}
