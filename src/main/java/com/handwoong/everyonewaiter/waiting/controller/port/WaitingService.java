package com.handwoong.everyonewaiter.waiting.controller.port;

import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.dto.WaitingCancel;
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;

public interface WaitingService {

    WaitingId register(WaitingRegister waitingRegister);

    void cancel(WaitingCancel waitingCancel);
}
