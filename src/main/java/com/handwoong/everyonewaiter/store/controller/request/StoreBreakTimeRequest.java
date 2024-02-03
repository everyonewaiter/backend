package com.handwoong.everyonewaiter.store.controller.request;

import com.handwoong.everyonewaiter.store.domain.DayOfWeek;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTime;
import com.handwoong.everyonewaiter.store.domain.StoreDaysOfWeek;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public record StoreBreakTimeRequest(
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime start,

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime end,

    @NotEmpty(message = "브레이크 타임의 요일을 하나 이상 등록해주세요.")
    List<DayOfWeek> daysOfWeek
) {

    public StoreBreakTime toDomain() {
        return StoreBreakTime.builder()
            .start(start)
            .end(end)
            .daysOfWeek(new StoreDaysOfWeek(daysOfWeek))
            .build();
    }
}
