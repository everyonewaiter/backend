package com.handwoong.everyonewaiter.common.mock;

import com.handwoong.everyonewaiter.common.application.port.UuidHolder;
import java.util.UUID;

public class FakeUuidHolder implements UuidHolder {

	private String uuidInput;

	public FakeUuidHolder() {
		this("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
	}

	public FakeUuidHolder(final String uuidInput) {
		this.uuidInput = uuidInput;
	}

	@Override
	public UUID generate() {
		return UUID.fromString(uuidInput);
	}

	public void setUuidInput(final String uuidInput) {
		this.uuidInput = uuidInput;
	}
}
