package com.handwoong.everyonewaiter.waiting.dto;

import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNumber;
import com.handwoong.everyonewaiter.waiting.domain.WaitingTurn;
import lombok.Builder;

@Builder
public record WaitingGenerateInfo(Store store, WaitingNumber number, WaitingTurn turn) {

}
