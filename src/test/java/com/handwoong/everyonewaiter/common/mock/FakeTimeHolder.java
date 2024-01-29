package com.handwoong.everyonewaiter.common.mock;

import com.handwoong.everyonewaiter.common.service.port.TimeHolder;

public record FakeTimeHolder(long millis) implements TimeHolder {

}
