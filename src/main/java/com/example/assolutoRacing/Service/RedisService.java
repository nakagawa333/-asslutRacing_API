package com.example.assolutoRacing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * Redisサービスクラス
 * @author nakagawa.so
 *
 */
@Service
public class RedisService {
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	
    /**
     * Redisからキーに該当するデータを取得する
     * @param key キー
     * @return 
     */
    public String get(String key) {
    	return redisTemplate.opsForValue().get(key);
    }
    
    /**
     * Redisにキーと値を設定する
     * @param key キー
     * @param value 値
     */
    public void set(String key,String value) {
    	redisTemplate.opsForValue().set(key, value);
    }
    
    public void delete(String key) {
    	redisTemplate.delete(key);
    }
    
}
