package com.alsace.exchange.web.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.net.UnknownHostException;

@Configuration
public class RedisConfig {

  @Bean
  public JsonRedisTemplate jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    JsonRedisTemplate template = new JsonRedisTemplate();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }


}
