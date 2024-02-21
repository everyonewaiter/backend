package com.handwoong.everyonewaiter.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.Collection;
import org.junit.jupiter.api.Test;

class AggregateRootTest {

	@Test
	void Should_ReturnEvent_When_RegisterEvent() {
		// given
		final AggregateRoot root =
				mock(AggregateRoot.class, withSettings().useConstructor().defaultAnswer(CALLS_REAL_METHODS));

		// when
		final TestEvent event = root.registerEvent(new TestEvent("테스트 이벤트"));

		// then
		assertThat(event.name).isEqualTo("테스트 이벤트");
	}

	@Test
	void Should_GetAllRegisteredEvent_When_DomainEvents() {
		// given
		final AggregateRoot root =
				mock(AggregateRoot.class, withSettings().useConstructor().defaultAnswer(CALLS_REAL_METHODS));
		root.registerEvent(new TestEvent("이벤트1"));
		root.registerEvent(new TestEvent("이벤트2"));
		root.registerEvent(new TestEvent("이벤트3"));

		// when
		final Collection<Object> events = root.domainEvents();

		// then
		assertThat(events).hasSize(3).extracting("name").contains("이벤트1", "이벤트2", "이벤트3");
	}

	@Test
	void Should_RemoveAllRegisteredEvent_When_ClearDomainEvents() {
		// given
		final AggregateRoot root =
				mock(AggregateRoot.class, withSettings().useConstructor().defaultAnswer(CALLS_REAL_METHODS));
		root.registerEvent(new TestEvent("이벤트1"));
		root.registerEvent(new TestEvent("이벤트2"));
		root.registerEvent(new TestEvent("이벤트3"));

		// when
		root.clearDomainEvents();
		final Collection<Object> events = root.domainEvents();

		// then
		assertThat(events).isEmpty();
	}

	private record TestEvent(String name) {

	}
}
