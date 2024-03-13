package com.handwoong.everyonewaiter.notification.infrastructure;

import com.handwoong.everyonewaiter.notification.application.port.NotificationRepository;
import com.handwoong.everyonewaiter.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

	private final NotificationJpaRepository notificationJpaRepository;

	@Override
	public Notification save(final Notification notification) {
		return notificationJpaRepository.save(NotificationEntity.from(notification)).toModel();
	}
}
