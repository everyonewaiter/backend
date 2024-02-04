package com.handwoong.everyonewaiter.user.infrastructure;

import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UnauthorizedAccessException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    public static Username getAuthenticationUsername() {
        final Authentication authentication = getAuthentication();
        if (Objects.isNull(authentication) || authentication.getPrincipal() instanceof String) {
            throw new UnauthorizedAccessException();
        }
        return new Username(authentication.getName());
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
