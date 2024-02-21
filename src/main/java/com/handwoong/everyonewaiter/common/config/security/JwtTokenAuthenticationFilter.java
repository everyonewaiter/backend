package com.handwoong.everyonewaiter.common.config.security;

import com.handwoong.everyonewaiter.common.exception.InvalidJwtTokenException;
import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtTokenProvider;
import com.handwoong.everyonewaiter.common.infrastructure.jwt.TokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends GenericFilter {

	private final transient JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilter(
			final ServletRequest servletRequest,
			final ServletResponse servletResponse,
			final FilterChain filterChain
	) throws IOException, ServletException {
		final String parsedJwtToken = parseJwtTokenFromRequest(servletRequest);
		saveAuthentication(parsedJwtToken);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private String parseJwtTokenFromRequest(final ServletRequest servletRequest) {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	private void saveAuthentication(final String token) {
		try {
			final TokenInfo tokenInfo = jwtTokenProvider.parseToken(token, "roles");
			final Authentication authentication = createAuthenticationFromTokenInfo(tokenInfo);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (final InvalidJwtTokenException exception) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}
	}

	private Authentication createAuthenticationFromTokenInfo(final TokenInfo tokenInfo) {
		final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(tokenInfo.claimValue()));
		final User principal = new User(tokenInfo.subject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}
}
