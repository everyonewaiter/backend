package com.handwoong.everyonewaiter.store.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreJpaRepository extends JpaRepository<StoreEntity, Long> {

    Optional<StoreEntity> findByIdAndUserId(Long storeId, Long userId);
}
