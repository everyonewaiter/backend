package com.handwoong.everyonewaiter.common.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AggregateRoot {

	private final List<Object> domainEvents = new ArrayList<>();

	protected <T> T registerEvent(final T event) {
		Assert.notNull(event, "Domain event must not be null");
		this.domainEvents.add(event);
		return event;
	}

	protected Collection<Object> domainEvents() {
		return Collections.unmodifiableList(this.domainEvents);
	}

	protected void clearDomainEvents() {
		this.domainEvents.clear();
	}
}
