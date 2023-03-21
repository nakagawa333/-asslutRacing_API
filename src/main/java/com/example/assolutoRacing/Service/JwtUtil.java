package com.example.assolutoRacing.Service;

import java.sql.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


/**
 * jwt設定クラス
 * @author nakagawa.so
 *
 */
@Service
@Transactional
public class JwtUtil {
	
	public byte[] getKeyBytes() {
		String base64SecretKey = "secretkeysecretkeysecretkeysecretkeysecretkeysecre";
		byte[] keyBytes = Base64.getDecoder().decode(base64SecretKey);	
		return keyBytes;
	}
	
	public SecretKey createKey() {
		byte[] keyBytes = getKeyBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String,Object> cliams = new HashMap<>();
		return createToken(cliams,userDetails.getUsername());
	}
	
	public String createToken(Map<String,Object> cliams,String username) {
		return Jwts.builder().setClaims(cliams).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
				.signWith(this.createKey(), SignatureAlgorithm.HS512).compact();
	}
	
	public Claims getAuthentication(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getKeyBytes())
				.build()
				.parseClaimsJwt(token)
				.getBody();
	}
}
