package com.handwoong.everyonewaiter.common.infrastructure.jwt;

import java.util.Date;
import lombok.Builder;

@Builder
public record TokenInfo(String subject, String claimKey, String claimValue, Date expire) {

}
