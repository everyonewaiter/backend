package com.handwoong.everyonewaiter.waiting.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingJpaRepository extends JpaRepository<WaitingEntity, Long> {

}
