package com.handwoong.everyonewaiter.store.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.store.domain.StoreBreakTime;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimeId;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class StoreBreakTimeEntityTest {

    @Test
    void Should_CreateEntity_When_FromModel() {
        // given
        final StoreBreakTime storeBreakTime = StoreBreakTime.builder()
            .id(new StoreBreakTimeId(1L))
            .start(LocalTime.of(15, 0, 0))
            .end(LocalTime.of(16, 30, 0))
            .build();

        // when
        final StoreBreakTimeEntity storeBreakTimeEntity = StoreBreakTimeEntity.from(storeBreakTime);

        // then
        assertThat(storeBreakTimeEntity).extracting("id").isEqualTo(1L);
    }

    @Test
    void Should_CreateDomain_When_ToModel() {
        // given
        final StoreBreakTime storeBreakTime = StoreBreakTime.builder()
            .id(new StoreBreakTimeId(1L))
            .start(LocalTime.of(15, 0, 0))
            .end(LocalTime.of(16, 30, 0))
            .build();
        final StoreBreakTimeEntity storeBreakTimeEntity = StoreBreakTimeEntity.from(storeBreakTime);

        // when
        final StoreBreakTime result = storeBreakTimeEntity.toModel();

        // then
        assertThat(result.getId().value()).isEqualTo(1L);
    }
}
