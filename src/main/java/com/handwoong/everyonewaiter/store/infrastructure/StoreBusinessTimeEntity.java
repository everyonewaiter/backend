package com.handwoong.everyonewaiter.store.infrastructure;

import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTime;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimeId;
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
@Table(name = "store_business_time")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreBusinessTimeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalTime open;

    @NotNull
    private LocalTime close;

    @NotNull
    @Convert(converter = DaysOfWeekConverter.class)
    private StoreDaysOfWeek daysOfWeek;

    public static StoreBusinessTimeEntity from(final StoreBusinessTime storeBusinessTime) {
        final StoreBusinessTimeEntity storeBusinessTimeEntity = new StoreBusinessTimeEntity();
        storeBusinessTimeEntity.id =
            Objects.isNull(storeBusinessTime.getId()) ? null : storeBusinessTime.getId().value();
        storeBusinessTimeEntity.open = storeBusinessTime.getOpen();
        storeBusinessTimeEntity.close = storeBusinessTime.getClose();
        storeBusinessTimeEntity.daysOfWeek = storeBusinessTime.getDaysOfWeek();
        return storeBusinessTimeEntity;
    }

    public StoreBusinessTime toModel() {
        return StoreBusinessTime.builder()
            .id(new StoreBusinessTimeId(id))
            .open(open)
            .close(close)
            .daysOfWeek(daysOfWeek)
            .timestamp(getDomainTimestamp())
            .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final StoreBusinessTimeEntity that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
