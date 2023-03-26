package com.example.assolutoRacing.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.assolutoRacing.Service.CustomUserDetailsService;
import com.example.assolutoRacing.Service.JwtUtil;

/**
 * ログイン後にアクセスされた際のトークンチェックを行う
 * @author nakagawa.so
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	JwtRequestFilter(CustomUserDetailsService customUserDetailsService,JwtUtil jwtUtil){
		this.customUserDetailsService = customUserDetailsService;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse, 
			FilterChain filterChain) throws ServletException, IOException{
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String username = "";
		String jwtToken = "";
		
		if(StringUtils.isNoneBlank(authorizationHeader) && authorizationHeader.startsWith("Basic")) {
			//jwtトークン
			jwtToken = authorizationHeader.substring(6);			
			//ユーザー名
			username = jwtUtil.extractUsername(jwtToken);
		} else {
			//トークンが存在しない場合、セッションを維持しない。
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		
		if(StringUtils.isNoneBlank(username)) {
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			
			//暫定処理
			if(jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
	
}
