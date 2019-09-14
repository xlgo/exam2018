package cn.hnzxl.exam.base.configuration;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;

@Configuration
public class JedisConfiguration {
	@Value("${jedis.hostName}")
	private String hostName;
	@Value("${jedis.port}")
	private int port;
	@Value("#{'${jedis.sentinels.hostAndPort}'.split(',')}")
	private Set<String> sentinels;
	@Value("${jedis.sentinels.masterName}")
	private String masterName;
	@Value("${jedis.sentinels.password}")
	private String password;
	@Value("${jedis.pool.maxTotal}")
	private int maxTotal;
	@Value("${jedis.pool.maxIdle}")
	private int maxIdle;
	@Value("${jedis.pool.minIdle}")
	private int minIdle;

	// @Bean
	public JedisSentinelPool jedisSentinelPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		return new JedisSentinelPool(masterName, sentinels, config, password);
	}
	
	@Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(hostName);
        jedisConnectionFactory.setPort(port);
       return jedisConnectionFactory;
    }
	
	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate srt = new StringRedisTemplate(redisConnectionFactory);
		srt.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		srt.opsForList().leftPush("系统信息:系统启动时间", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		return srt;
	}
	/*
	 * @Bean public LettuceConnectionFactory lettuceConnectionFactory(){ return
	 * new LettuceConnectionFactory(); }
	 * 
	 * @Bean public RedisSentinelConfiguration redisSentinelConfiguration() {
	 * RedisSentinelConfiguration conf = new
	 * RedisSentinelConfiguration(masterName, sentinels); return conf; }
	 * 
	 * @Bean public LettuceConnectionFactory connectionFactory() { return new
	 * LettuceConnectionFactory(); }
	 */
	/*
	 * @Bean public LettuceConnectionFactory connectionFactory() {
	 * LettuceConnectionFactory lettuceConnectionFactory = new
	 * LettuceConnectionFactory(redisSentinelConfiguration()); return
	 * lettuceConnectionFactory; }
	 * 
	 * @Bean public ConfigureRedisAction configureRedisAction() { return
	 * ConfigureRedisAction.NO_OP; }
	 * 
	 * @Bean public RedisSentinelConfiguration redisSentinelConfiguration() {
	 * RedisSentinelConfiguration conf = new
	 * RedisSentinelConfiguration(masterName, sentinels); return conf; }
	 * 
	 * @Bean public RedisHttpSessionConfiguration
	 * redisHttpSessionConfiguration() { RedisHttpSessionConfiguration
	 * redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
	 * return redisHttpSessionConfiguration; }
	 */

}
