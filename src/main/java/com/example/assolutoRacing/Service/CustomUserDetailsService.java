package com.example.assolutoRacing.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Bean.AuthUserRes;
import com.example.assolutoRacing.Dto.CustromUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired 
	UserService userService;
	@Override
	public UserDetails loadUserByUsername(String username) {
		AuthUserRes authUserRes = new AuthUserRes();
		
		if(authUserRes == null) {
			throw new UsernameNotFoundException("ユーザーが存在しません");
		}
		CustromUserDetails custromUserDetails = new CustromUserDetails(authUserRes);
		return custromUserDetails;
	}
}
