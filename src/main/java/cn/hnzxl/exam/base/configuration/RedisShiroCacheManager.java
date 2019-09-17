package cn.hnzxl.exam.base.configuration;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisShiroCacheManager  extends AbstractCacheManager{
	private RedisTemplate<String, Object> redisTemplate;
	@Override
	protected Cache<String, Object> createCache(String name) throws CacheException {
		return new ShiroCache(redisTemplate);
	}
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
}
