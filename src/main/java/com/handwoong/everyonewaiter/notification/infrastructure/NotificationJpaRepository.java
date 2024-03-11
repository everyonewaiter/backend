package com.handwoong.everyonewaiter.notification.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {

}
