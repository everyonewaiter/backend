package com.handwoong.everyonewaiter.common.domain;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class DomainEventPublisher {

	private final ApplicationEventPublisher eventPub;

	@Pointcut("within(@org.springframework.stereotype.Repository *)")
	public void repository() {
	}

	@Pointcut("execution(* *..save*(..))")
	public void saveMethod() {
	}

	@Pointcut("execution(* *..delete*(..))")
	public void deleteMethod() {
	}

	@AfterReturning(value = "repository() && (saveMethod() || deleteMethod())")
	public void invoke(final JoinPoint joinPoint) {
		for (final Object arg : joinPoint.getArgs()) {
			if (arg instanceof final AggregateRoot root) {
				root.domainEvents().forEach(eventPub::publishEvent);
				root.clearDomainEvents();
			}
		}
	}
}
