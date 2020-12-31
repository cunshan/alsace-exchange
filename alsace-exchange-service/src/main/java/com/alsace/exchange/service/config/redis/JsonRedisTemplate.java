package com.alsace.exchange.service.config.redis;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class JsonRedisTemplate extends RedisTemplate<String,Object> {

  public JsonRedisTemplate(){
    setKeySerializer(RedisSerializer.string());
    setValueSerializer(RedisSerializer.java());
    setHashKeySerializer(RedisSerializer.string());
    setHashValueSerializer(RedisSerializer.java());
  }

  public JsonRedisTemplate(RedisConnectionFactory connectionFactory) {
    this();
    setConnectionFactory(connectionFactory);
    afterPropertiesSet();
  }

  protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
    return new DefaultStringRedisConnection(connection);
  }

}
