package com.handwoong.everyonewaiter.store.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.store.domain.StoreOption;
import com.handwoong.everyonewaiter.store.dto.StoreCreate;
import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.UserRole;
import com.handwoong.everyonewaiter.user.domain.UserStatus;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoreServiceImplTest {

    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();
        final User user = User.builder()
            .id(new UserId(1L))
            .username(new Username("handwoong"))
            .password(new Password("password"))
            .phoneNumber(new PhoneNumber("01012345678"))
            .role(UserRole.ROLE_USER)
            .status(UserStatus.ACTIVE)
            .build();
        testContainer.userRepository.save(user);
    }

    @Test
    void Should_Create_When_ValidStoreCreate() {
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
        final StoreId storeId = testContainer.storeService.create(new Username("handwoong"), storeCreate);

        // then
        assertThat(storeId).isEqualTo(new StoreId(1L));
    }
}
