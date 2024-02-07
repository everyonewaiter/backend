package com.handwoong.everyonewaiter.waiting.application.port;

import com.handwoong.everyonewaiter.waiting.domain.Waiting;

public interface WaitingRepository {

    Waiting save(Waiting waiting);
}
