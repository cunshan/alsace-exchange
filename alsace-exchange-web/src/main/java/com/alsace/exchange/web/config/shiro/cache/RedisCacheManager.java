package com.alsace.exchange.web.config.shiro.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCacheManager extends AbstractCacheManager {

  private final RedisTemplate<String,Object> redisTemplate;

  public RedisCacheManager(RedisTemplate<String,Object> redisTemplate){
    this.redisTemplate = redisTemplate;
  }

  @Override
  protected Cache createCache(String name) throws CacheException {
    return new RedisCache(this.redisTemplate);
  }
}
