package com.handwoong.everyonewaiter.user.infrastructure;

import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private final transient User user;
	private final List<GrantedAuthority> roles = new ArrayList<>();

	public CustomUserDetails(final User user) {
		this.user = user;
		roles.add(new SimpleGrantedAuthority(user.getRole().name()));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return user.getPassword().toString();
	}

	@Override
	public String getUsername() {
		return user.getUsername().toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.checkStatusDifference(UserStatus.SLEEP);
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.checkStatusDifference(UserStatus.LOCK);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.checkStatusDifference(UserStatus.INACTIVE);
	}

	@Override
	public boolean isEnabled() {
		return user.checkStatusDifference(UserStatus.LEAVE);
	}
}
