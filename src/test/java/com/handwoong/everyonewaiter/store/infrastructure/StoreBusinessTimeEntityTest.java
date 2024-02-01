package com.handwoong.everyonewaiter.store.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.store.domain.StoreBusinessTime;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimeId;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class StoreBusinessTimeEntityTest {

    @Test
    void Should_CreateEntity_When_FromModel() {
        // given
        final StoreBusinessTime storeBusinessTime = StoreBusinessTime.builder()
            .id(new StoreBusinessTimeId(1L))
            .open(LocalTime.of(11, 0, 0))
            .close(LocalTime.of(21, 0, 0))
            .build();

        // when
        final StoreBusinessTimeEntity storeBusinessTimeEntity = StoreBusinessTimeEntity.from(storeBusinessTime);

        // then
        assertThat(storeBusinessTimeEntity).extracting("id").isEqualTo(1L);
    }

    @Test
    void Should_CreateDomain_When_ToModel() {
        // given
        final StoreBusinessTime storeBusinessTime = StoreBusinessTime.builder()
            .id(new StoreBusinessTimeId(1L))
            .open(LocalTime.of(11, 0, 0))
            .close(LocalTime.of(21, 0, 0))
            .build();
        final StoreBusinessTimeEntity storeBusinessTimeEntity = StoreBusinessTimeEntity.from(storeBusinessTime);

        // when
        final StoreBusinessTime result = storeBusinessTimeEntity.toModel();

        // then
        assertThat(result.getId().value()).isEqualTo(1L);
    }
}
