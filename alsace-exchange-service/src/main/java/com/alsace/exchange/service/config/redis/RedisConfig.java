package com.alsace.exchange.service.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisConfig {

  @Bean
  public JsonRedisTemplate jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    JsonRedisTemplate template = new JsonRedisTemplate();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }


}
