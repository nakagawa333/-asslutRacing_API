package com.example.assolutoRacing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis Bean定義クラス
 * @author nakagawa.so
 *
 */
@Configuration
public class RedisConfig {

  @Bean
  JedisConnectionFactory redisConnectionFactory() {
	  RedisStandaloneConfiguration config= new RedisStandaloneConfiguration();
	  JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(config);
	  jedisConnectionFactory.afterPropertiesSet();
	  return jedisConnectionFactory;
  }

  @Bean
  public RedisTemplate<String,String> redisTemplate(){
	  RedisTemplate<String,String> redisTemplate = new RedisTemplate<String, String>();
      StringRedisSerializer serializer = new StringRedisSerializer();
      redisTemplate.setDefaultSerializer(serializer);
      redisTemplate.setKeySerializer(serializer);
      redisTemplate.setValueSerializer(serializer);
      redisTemplate.setConnectionFactory(redisConnectionFactory());
      redisTemplate.afterPropertiesSet();
	  return redisTemplate;
  }
}
