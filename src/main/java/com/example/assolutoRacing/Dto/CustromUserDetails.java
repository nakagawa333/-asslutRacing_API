package com.example.assolutoRacing.Dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.assolutoRacing.Bean.AuthUserRes;

import lombok.Getter;
import lombok.Setter;

@Setter
public class CustromUserDetails implements UserDetails{
	
	public AuthUserRes authUserRes;
	
	public CustromUserDetails(AuthUserRes authUserRes){
		this.authUserRes = authUserRes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return authUserRes.getPassword();
	}

	@Override
	public String getUsername() {
		return authUserRes.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
