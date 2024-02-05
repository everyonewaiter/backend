package com.handwoong.everyonewaiter.store.controller.request;

import static com.handwoong.everyonewaiter.store.controller.request.StoreBusinessTimeRequest.MIN_BUSINESS_TIME_MESSAGE;
import static com.handwoong.everyonewaiter.store.domain.LandlineNumber.LANDLINE_NUMBER_FORMAT_MESSAGE;
import static com.handwoong.everyonewaiter.store.domain.LandlineNumber.LANDLINE_NUMBER_REGEX;
import static com.handwoong.everyonewaiter.store.domain.StoreName.MAX_LENGTH;
import static com.handwoong.everyonewaiter.store.domain.StoreName.STORE_NAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.store.domain.StoreName.STORE_NAME_MAX_LENGTH_MESSAGE;

import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.store.dto.StoreUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public record StoreUpdateRequest(
    @NotNull
    Long id,

    @NotBlank(message = STORE_NAME_EMPTY_MESSAGE)
    @Size(max = MAX_LENGTH, message = STORE_NAME_MAX_LENGTH_MESSAGE)
    String name,

    @NotBlank(message = LANDLINE_NUMBER_FORMAT_MESSAGE)
    @Pattern(regexp = LANDLINE_NUMBER_REGEX, message = LANDLINE_NUMBER_FORMAT_MESSAGE)
    String landlineNumber,

    @NotNull
    List<@Valid StoreBreakTimeRequest> breakTimes,

    @NotEmpty(message = MIN_BUSINESS_TIME_MESSAGE)
    List<@Valid StoreBusinessTimeRequest> businessTimes
) {

    public StoreUpdate toDomainDto() {
        return StoreUpdate.builder()
            .id(new StoreId(id))
            .name(new StoreName(name))
            .landlineNumber(new LandlineNumber(landlineNumber))
            .businessTimes(
                new StoreBusinessTimes(
                    businessTimes.stream()
                        .map(StoreBusinessTimeRequest::toDomain)
                        .toList()
                )
            )
            .breakTimes(
                new StoreBreakTimes(
                    breakTimes.stream()
                        .map(StoreBreakTimeRequest::toDomain)
                        .toList()
                )
            )
            .build();
    }
}
