package cn.hnzxl.exam.base.configuration;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

public class ShiroCache implements Cache<String, Object>{
	private RedisTemplate<String, Object> redisTemplate;
	public ShiroCache(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	@Override
	public Object get(String key) throws CacheException {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public Object put(String key, Object value) throws CacheException {
		redisTemplate.opsForValue().set(key,value,30,TimeUnit.MINUTES);
		return value;
	}

	@Override
	public Object remove(String key) throws CacheException {
		redisTemplate.delete(key);
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
	public Set<String> keys() {
		return null;
	}

	@Override
	public Collection<Object> values() {
		return null;
	}
	
}
