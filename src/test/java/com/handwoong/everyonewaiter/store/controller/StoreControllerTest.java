package com.handwoong.everyonewaiter.store.controller;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.store.controller.request.StoreBreakTimeRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreBusinessTimeRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateOptionRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreOptionUpdateRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreUpdateRequest;
import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTime;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimeId;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTime;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimeId;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreDaysOfWeek;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.store.domain.StoreOption;
import com.handwoong.everyonewaiter.store.domain.StoreOptionId;
import com.handwoong.everyonewaiter.store.domain.StoreStatus;
import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.UserRole;
import com.handwoong.everyonewaiter.user.domain.UserStatus;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class StoreControllerTest {

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

        final Store store = Store.builder()
            .id(new StoreId(1L))
            .userId(new UserId(1L))
            .name(new StoreName("나루"))
            .landlineNumber(new LandlineNumber("0551234567"))
            .status(StoreStatus.CLOSE)
            .businessTimes(
                new StoreBusinessTimes(
                    List.of(
                        StoreBusinessTime.builder()
                            .id(new StoreBusinessTimeId(1L))
                            .open(LocalTime.of(11, 0, 0))
                            .close(LocalTime.of(21, 0, 0))
                            .daysOfWeek(
                                new StoreDaysOfWeek(
                                    List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
                                )
                            )
                            .build()
                    )
                )
            )
            .breakTimes(
                new StoreBreakTimes(
                    List.of(
                        StoreBreakTime.builder()
                            .id(new StoreBreakTimeId(1L))
                            .start(LocalTime.of(15, 0, 0))
                            .end(LocalTime.of(16, 30, 0))
                            .daysOfWeek(
                                new StoreDaysOfWeek(
                                    List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
                                )
                            )
                            .build(),
                        StoreBreakTime.builder()
                            .id(new StoreBreakTimeId(2L))
                            .start(LocalTime.of(15, 30, 0))
                            .end(LocalTime.of(17, 0, 0))
                            .daysOfWeek(
                                new StoreDaysOfWeek(
                                    List.of(SATURDAY, SUNDAY)
                                )
                            )
                            .build()
                    )
                )
            )
            .option(
                StoreOption.builder()
                    .id(new StoreOptionId(1L))
                    .useBreakTime(true)
                    .useWaiting(true)
                    .useOrder(true)
                    .build()
            )
            .build();
        testContainer.storeRepository.save(store);
    }

    @Test
    void Should_Create_When_ValidRequest() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("handwoong"));
        final List<StoreBreakTimeRequest> breakTimes = getBreakTimeRequests();
        final List<StoreBusinessTimeRequest> businessTimes = getBusinessTimeRequests();
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
        final List<StoreBreakTimeRequest> breakTimes = getBreakTimeRequests();
        final List<StoreBusinessTimeRequest> businessTimes = getBusinessTimeRequests();
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
        final List<StoreBreakTimeRequest> breakTimes = getBreakTimeRequests();
        final List<StoreBusinessTimeRequest> businessTimes = getBusinessTimeRequests();
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
        final List<StoreBreakTimeRequest> breakTimes = getBreakTimeRequests();
        final List<StoreBusinessTimeRequest> businessTimes = getBusinessTimeRequests();
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

    private List<StoreBusinessTimeRequest> getBusinessTimeRequests() {
        return List.of(
            new StoreBusinessTimeRequest(
                LocalTime.of(11, 0, 0),
                LocalTime.of(21, 0, 0),
                List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
            )
        );
    }

    private List<StoreBreakTimeRequest> getBreakTimeRequests() {
        return List.of(
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
    }
}
