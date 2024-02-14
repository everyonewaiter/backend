package com.handwoong.everyonewaiter.store.controller;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.store.controller.request.StoreBreakTimeRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreBusinessTimeRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateOptionRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreOptionUpdateRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreUpdateRequest;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class StoreControllerTest {

    private final List<StoreBusinessTimeRequest> businessTimes = List.of(
        new StoreBusinessTimeRequest(
            LocalTime.of(11, 0, 0),
            LocalTime.of(21, 0, 0),
            List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
        )
    );
    private final List<StoreBreakTimeRequest> breakTimes = List.of(
        new StoreBreakTimeRequest(
            LocalTime.of(15, 0, 0),
            LocalTime.of(16, 30, 0),
            List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
        ),
        new StoreBreakTimeRequest(
            LocalTime.of(15, 30, 0),
            LocalTime.of(17, 0, 0),
            List.of(SATURDAY, SUNDAY)
        )
    );

    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();

        final User user = aUser().build();
        testContainer.userRepository.save(user);

        final Store store = aStore().build();
        testContainer.storeRepository.save(store);
    }

    @Test
    void Should_Create_When_ValidRequest() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("handwoong"));
        final StoreCreateRequest request = new StoreCreateRequest(
            "나루", "0551234567", breakTimes, businessTimes, new StoreCreateOptionRequest(true, true, true));

        // when
        final ResponseEntity<ApiResponse<Void>> response = testContainer.storeController.create(request);
        final ApiResponse<Void> result = response.getBody();

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(result).extracting("resultCode").isEqualTo(ResultCode.SUCCESS);
    }

    @Test
    void Should_ThrowException_When_CreateUsernameNotFound() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("notfound"));
        final StoreCreateRequest request = new StoreCreateRequest(
            "나루", "0551234567", breakTimes, businessTimes, new StoreCreateOptionRequest(true, true, true));

        // expect
        assertThatThrownBy(() -> testContainer.storeController.create(request))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("사용자를 찾을 수 없습니다.");
    }

    @Test
    void Should_Update_When_ValidRequest() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("handwoong"));
        final StoreUpdateRequest request =
            new StoreUpdateRequest(1L, "나루 레스토랑", "021234567", breakTimes, businessTimes);

        // when
        final ResponseEntity<ApiResponse<Void>> response = testContainer.storeController.update(request);
        final ApiResponse<Void> result = response.getBody();

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(result).extracting("resultCode").isEqualTo(ResultCode.SUCCESS);
    }

    @Test
    void Should_ThrowException_When_UpdateUsernameNotFound() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("notfound"));
        final StoreUpdateRequest request =
            new StoreUpdateRequest(1L, "나루 레스토랑", "021234567", breakTimes, businessTimes);

        // expect
        assertThatThrownBy(() -> testContainer.storeController.update(request))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("사용자를 찾을 수 없습니다.");
    }

    @Test
    void Should_UpdateOption_When_ValidRequest() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("handwoong"));
        final StoreOptionUpdateRequest request = new StoreOptionUpdateRequest(1L, false, false, false);

        // when
        final ResponseEntity<ApiResponse<Void>> response = testContainer.storeController.update(request);
        final ApiResponse<Void> result = response.getBody();

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(result).extracting("resultCode").isEqualTo(ResultCode.SUCCESS);
    }

    @Test
    void Should_ThrowException_When_UpdateOptionUsernameNotFound() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("notfound"));
        final StoreOptionUpdateRequest request = new StoreOptionUpdateRequest(1L, false, false, false);

        // expect
        assertThatThrownBy(() -> testContainer.storeController.update(request))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("사용자를 찾을 수 없습니다.");
    }

    @Test
    void Should_DeleteStore_When_ValidStoreId() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("handwoong"));
        final UserId userId = new UserId(1L);
        final StoreId storeId = new StoreId(1L);

        // when
        testContainer.storeController.delete(1L);

        // then
        assertThatThrownBy(() -> testContainer.storeRepository.findByIdAndUserIdOrElseThrow(storeId, userId))
            .isInstanceOf(StoreNotFoundException.class);
    }
}
