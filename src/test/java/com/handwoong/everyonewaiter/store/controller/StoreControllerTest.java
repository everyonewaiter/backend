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
    }

    @Test
    void Should_Create_When_ValidRequest() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("handwoong"));
        final StoreCreateRequest request = new StoreCreateRequest("나루", "0551234567",
            List.of(
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
            ),
            List.of(
                new StoreBusinessTimeRequest(
                    LocalTime.of(11, 0, 0),
                    LocalTime.of(21, 0, 0),
                    List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
                )
            ),
            new StoreCreateOptionRequest(true, true, true)
        );

        // when
        final ResponseEntity<ApiResponse<Void>> response = testContainer.storeController.create(request);
        final ApiResponse<Void> result = response.getBody();

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(result).extracting("resultCode").isEqualTo(ResultCode.SUCCESS);
    }

    @Test
    void Should_ThrowException_When_UsernameNotFound() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("notfound"));
        final StoreCreateRequest request = new StoreCreateRequest("나루", "0551234567",
            List.of(
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
            ),
            List.of(
                new StoreBusinessTimeRequest(
                    LocalTime.of(11, 0, 0),
                    LocalTime.of(21, 0, 0),
                    List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
                )
            ),
            new StoreCreateOptionRequest(true, true, true)
        );

        // expect
        assertThatThrownBy(() -> testContainer.storeController.create(request))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("사용자를 찾을 수 없습니다.");
    }
}
