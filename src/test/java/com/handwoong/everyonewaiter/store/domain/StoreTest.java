package com.handwoong.everyonewaiter.store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.store.dto.StoreCreate;
import com.handwoong.everyonewaiter.user.domain.UserId;
import java.util.List;
import org.junit.jupiter.api.Test;

class StoreTest {

    @Test
    void Should_StatusClose_When_Create() {
        // given
        final StoreCreate storeCreate = StoreCreate.builder()
            .name(new StoreName("나루"))
            .landlineNumber(new LandlineNumber("0551234567"))
            .businessTimes(new StoreBusinessTimes(List.of()))
            .breakTimes(new StoreBreakTimes(List.of()))
            .option(
                StoreOption.builder()
                    .useBreakTime(true)
                    .useWaiting(true)
                    .useOrder(true)
                    .build()
            )
            .build();

        // when
        final Store store = Store.create(new UserId(1L), storeCreate);

        // then
        assertThat(store.getStatus()).isEqualTo(StoreStatus.CLOSE);
    }
}
