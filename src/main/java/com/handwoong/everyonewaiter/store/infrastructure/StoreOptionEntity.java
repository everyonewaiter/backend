package com.handwoong.everyonewaiter.store.infrastructure;

import com.handwoong.everyonewaiter.store.domain.StoreOption;
import com.handwoong.everyonewaiter.store.domain.StoreOptionId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "store_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private boolean useBreakTime;

    @NotNull
    private boolean useWaiting;

    @NotNull
    private boolean useOrder;

    public static StoreOptionEntity from(final StoreOption storeOption) {
        final StoreOptionEntity storeOptionEntity = new StoreOptionEntity();
        storeOptionEntity.id = Objects.isNull(storeOption.getId()) ? null : storeOption.getId().value();
        storeOptionEntity.useBreakTime = storeOption.isUseBreakTime();
        storeOptionEntity.useWaiting = storeOption.isUseWaiting();
        storeOptionEntity.useOrder = storeOption.isUseOrder();
        return storeOptionEntity;
    }

    public StoreOption toModel() {
        return StoreOption.builder()
            .id(new StoreOptionId(id))
            .useBreakTime(useBreakTime)
            .useWaiting(useWaiting)
            .useOrder(useOrder)
            .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final StoreOptionEntity that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
