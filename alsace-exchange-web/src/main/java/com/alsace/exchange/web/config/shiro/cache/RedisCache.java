package com.alsace.exchange.web.config.shiro.cache;

import com.alsace.exchange.common.utils.JsonUtils;
import com.alsace.exchange.web.common.WebConstants;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisCache implements Cache<Object, Object> {

  private final RedisTemplate<String,Object> redisTemplate;

  public RedisCache(RedisTemplate<String,Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public Object get(Object key) throws CacheException {
    if(key == null){
      return null;
    }
    return redisTemplate.opsForValue().get(WebConstants.SHIRO_CACHE_PREFIX + key);
  }

  @Override
  public Object put(Object key, Object value) throws CacheException {
    redisTemplate.opsForValue().set(WebConstants.SHIRO_CACHE_PREFIX + key, value,WebConstants.JWT_TOKEN_TIMEOUT, TimeUnit.MINUTES);
    return value;
  }

  @Override
  public Object remove(Object key) throws CacheException {
    redisTemplate.delete(WebConstants.SHIRO_CACHE_PREFIX + key);
    return null;
  }

  @Override
  public void clear() throws CacheException {

  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public Set<Object> keys() {
    return null;
  }

  @Override
  public Collection<Object> values() {
    return null;
  }
}
