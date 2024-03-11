package com.handwoong.everyonewaiter.notification.domain;

public record NotificationId(Long value) {

	@Override
	public String toString() {
		return value.toString();
	}
}
