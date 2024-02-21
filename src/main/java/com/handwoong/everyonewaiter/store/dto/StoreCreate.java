package com.handwoong.everyonewaiter.store.dto;

import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.store.domain.StoreOption;
import lombok.Builder;

@Builder
public record StoreCreate(
		StoreName name,
		LandlineNumber landlineNumber,
		StoreBusinessTimes businessTimes,
		StoreBreakTimes breakTimes,
		StoreOption option
) {

}
