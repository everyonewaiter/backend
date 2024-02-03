package com.handwoong.everyonewaiter.store.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.store.domain.StoreOption;
import com.handwoong.everyonewaiter.store.domain.StoreOptionId;
import org.junit.jupiter.api.Test;

class StoreOptionEntityTest {

    @Test
    void Should_CreateEntity_When_FromModel() {
        // given
        final StoreOption storeOption = StoreOption.builder()
            .id(new StoreOptionId(1L))
            .useBreakTime(true)
            .useOrder(true)
            .useWaiting(true)
            .build();

        // when
        final StoreOptionEntity storeOptionEntity = StoreOptionEntity.from(storeOption);

        // then
        assertThat(storeOptionEntity).extracting("id").isEqualTo(1L);
    }

    @Test
    void Should_CreateDomain_When_ToModel() {
        // given
        final StoreOption storeOption = StoreOption.builder()
            .id(new StoreOptionId(1L))
            .useBreakTime(true)
            .useOrder(true)
            .useWaiting(true)
            .build();
        final StoreOptionEntity storeOptionEntity = StoreOptionEntity.from(storeOption);

        // when
        final StoreOption result = storeOptionEntity.toModel();

        // then
        assertThat(result.getId().value()).isEqualTo(1L);
    }
}
