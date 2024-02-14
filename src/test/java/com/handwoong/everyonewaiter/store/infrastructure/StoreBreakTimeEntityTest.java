package com.handwoong.everyonewaiter.store.infrastructure;

import static com.handwoong.everyonewaiter.util.Fixtures.aStoreBreakTime;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.store.domain.StoreBreakTime;
import org.junit.jupiter.api.Test;

class StoreBreakTimeEntityTest {

    @Test
    void Should_CreateEntity_When_FromModel() {
        // given
        final StoreBreakTime storeBreakTime = aStoreBreakTime().build();

        // when
        final StoreBreakTimeEntity storeBreakTimeEntity = StoreBreakTimeEntity.from(storeBreakTime);

        // then
        assertThat(storeBreakTimeEntity).extracting("id").isEqualTo(1L);
    }

    @Test
    void Should_CreateDomain_When_ToModel() {
        // given
        final StoreBreakTime storeBreakTime = aStoreBreakTime().build();
        final StoreBreakTimeEntity storeBreakTimeEntity = StoreBreakTimeEntity.from(storeBreakTime);

        // when
        final StoreBreakTime result = storeBreakTimeEntity.toModel();

        // then
        assertThat(result.getId().value()).isEqualTo(1L);
    }
}
