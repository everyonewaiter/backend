package com.handwoong.everyonewaiter.common.mock;

import com.handwoong.everyonewaiter.common.application.port.TimeHolder;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FakeTimeHolder implements TimeHolder {

	private long millis;

	public FakeTimeHolder() {
		this(123456789L);
	}

	public FakeTimeHolder(final long millis) {
		this.millis = millis;
	}

	@Override
	public long millis() {
		return millis;
	}

	public void setMillis(final LocalDateTime fixedTime) {
		final ZoneId zoneId = ZoneId.systemDefault();
		final Instant instant = fixedTime.atZone(zoneId).toInstant();
		this.millis = Clock.fixed(instant, zoneId).millis();
	}
}
