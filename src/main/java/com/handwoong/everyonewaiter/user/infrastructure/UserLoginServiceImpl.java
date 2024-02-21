package com.handwoong.everyonewaiter.user.infrastructure;

import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtTokenProvider;
import com.handwoong.everyonewaiter.common.infrastructure.jwt.TokenInfo;
import com.handwoong.everyonewaiter.user.application.port.UserLoginService;
import com.handwoong.everyonewaiter.user.dto.UserLogin;
import com.handwoong.everyonewaiter.user.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@Override
	public JwtToken login(final UserLogin userLogin) {
		final UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password());
		final Authentication authentication =
				authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		return jwtTokenProvider.createToken(authenticationTokenInfo(authentication));
	}

	private TokenInfo authenticationTokenInfo(final Authentication authentication) {
		final String username = authentication.getName();
		final String role = extractUserRole(authentication);
		return TokenInfo.builder()
				.subject(username)
				.claimKey("roles")
				.claimValue(role)
				.build();
	}

	private String extractUserRole(final Authentication authentication) {
		return authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.findAny()
				.orElseThrow(UnauthorizedAccessException::new);
	}
}
