package com.handwoong.everyonewaiter.common.mock;

import com.handwoong.everyonewaiter.common.service.port.TimeHolder;

public class FakeTimeHolder implements TimeHolder {

    private final long millis;

    public FakeTimeHolder(final long millis) {
        this.millis = millis;
    }

    @Override
    public long millis() {
        return millis;
    }
}
