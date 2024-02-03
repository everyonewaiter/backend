package com.handwoong.everyonewaiter.store.infrastructure;

import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTime;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimeId;
import com.handwoong.everyonewaiter.store.domain.StoreDaysOfWeek;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "store_break_time")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreBreakTimeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalTime start;

    @NotNull
    private LocalTime end;

    @NotNull
    @Convert(converter = DaysOfWeekConverter.class)
    private StoreDaysOfWeek daysOfWeek;

    public static StoreBreakTimeEntity from(final StoreBreakTime storeBreakTime) {
        final StoreBreakTimeEntity storeBreakTimeEntity = new StoreBreakTimeEntity();
        storeBreakTimeEntity.id = Objects.isNull(storeBreakTime.getId()) ? null : storeBreakTime.getId().value();
        storeBreakTimeEntity.start = storeBreakTime.getStart();
        storeBreakTimeEntity.end = storeBreakTime.getEnd();
        storeBreakTimeEntity.daysOfWeek = storeBreakTime.getDaysOfWeek();
        return storeBreakTimeEntity;
    }

    public StoreBreakTime toModel() {
        return StoreBreakTime.builder()
            .id(new StoreBreakTimeId(id))
            .start(start)
            .end(end)
            .daysOfWeek(daysOfWeek)
            .timestamp(getDomainTimestamp())
            .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final StoreBreakTimeEntity that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
