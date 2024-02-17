package com.handwoong.everyonewaiter.category.domain;

import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.application.port.UserRepository;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.infrastructure.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public void validate(final StoreId storeId) {
        getStore(storeId, getUser());
    }

    private User getUser() {
        final Username username = SecurityUtils.getAuthenticationUsername();
        return userRepository.findByUsernameOrElseThrow(username);
    }

    private void getStore(final StoreId storeId, final User user) {
        storeRepository.findByIdAndUserIdOrElseThrow(storeId, user.getId());
    }
}
