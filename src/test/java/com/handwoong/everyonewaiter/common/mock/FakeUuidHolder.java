package com.handwoong.everyonewaiter.common.mock;

import com.handwoong.everyonewaiter.common.application.port.UuidHolder;
import java.util.UUID;

public class FakeUuidHolder implements UuidHolder {

    private final String uuidInput;

    public FakeUuidHolder(final String uuidInput) {
        this.uuidInput = uuidInput;
    }

    @Override
    public UUID generate() {
        return UUID.fromString(uuidInput);
    }
}
