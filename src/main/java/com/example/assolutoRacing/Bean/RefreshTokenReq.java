package com.example.assolutoRacing.Bean;

import lombok.Data;
import lombok.Getter;

/**
 * リフレッシュトークンリクエストクラス
 * @author nakagawa.so
 *
 */
@Data
public class RefreshTokenReq {
	//リフレッシュトークン
	private String refreshToken;
}
