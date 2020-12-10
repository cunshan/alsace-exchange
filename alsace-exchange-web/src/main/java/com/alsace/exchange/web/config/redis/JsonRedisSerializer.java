package com.alsace.exchange.web.config.redis;

import com.alsace.exchange.common.utils.JsonUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

public class JsonRedisSerializer implements RedisSerializer<Object> {
  @Override
  public byte[] serialize(Object obj) throws SerializationException {
    return JsonUtils.toJson(obj).getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public Object deserialize(byte[] bytes) throws SerializationException {
    String value = bytes ==null? "":new String(bytes,StandardCharsets.UTF_8);
    return JsonUtils.fromJson(value,Object.class);
  }

}
