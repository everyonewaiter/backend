package com.handwoong.everyonewaiter.waiting.infrastructure;

import com.handwoong.everyonewaiter.waiting.application.port.WaitingRepository;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WaitingRepositoryImpl implements WaitingRepository {

    private final WaitingJpaRepository waitingJpaRepository;

    @Override
    public Waiting save(final Waiting waiting) {
        return waitingJpaRepository.save(WaitingEntity.from(waiting)).toModel();
    }
}
