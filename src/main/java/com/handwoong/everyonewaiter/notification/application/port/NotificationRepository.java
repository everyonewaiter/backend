package com.handwoong.everyonewaiter.notification.application.port;

import com.handwoong.everyonewaiter.notification.domain.Notification;

public interface NotificationRepository {

	Notification save(Notification notification);
}
