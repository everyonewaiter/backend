package com.handwoong.everyonewaiter.common.infrastructure;

import com.handwoong.everyonewaiter.common.service.port.TimeHolder;
import org.springframework.stereotype.Component;

@Component
public class SystemTimeHolder implements TimeHolder {

    @Override
    public long millis() {
        return System.currentTimeMillis();
    }
}
