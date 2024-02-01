package com.handwoong.everyonewaiter.common.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DomainTimestamp {

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
