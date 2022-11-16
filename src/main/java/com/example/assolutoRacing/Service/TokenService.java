package com.example.assolutoRacing.Service;

import java.nio.ByteBuffer;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;


/**
 * 
 * @author nakagawa.so
 * トークンサービス.
 */
@Service
public class TokenService {
	
	/**
	 * 
	 * メールアドレスからトークンを生成する.
	 * @param email メールアドレス
	 * @return トークン
	 */
	public String createToken(String email) {
		String token = "";
		
		try {
			ByteBuffer buf = ByteBuffer.allocate(48);
			//16byteのランダム文字列
			byte[] randomBytes = new byte[16];
			SecureRandom secureRandom = new SecureRandom();
			
			//32byteのemailハッシュ値
			byte[] emailHashBytes = DigestUtils.sha256(email + Hex.encodeHexString(randomBytes));
			buf.put(emailHashBytes);
			token = Base64.encodeBase64URLSafeString(buf.array());
		} catch(Exception e) {
			throw e;
		}
		return token;
	}
}
