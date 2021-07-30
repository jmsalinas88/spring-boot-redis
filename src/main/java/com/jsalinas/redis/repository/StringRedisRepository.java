package com.jsalinas.redis.repository;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StringRedisRepository {
	
	private final StringRedisTemplate stringRedisTemplate;

	public StringRedisRepository(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}
	
	public void add(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	public void send(String key, String value) {
		stringRedisTemplate.convertAndSend(key, value);
	}
	
	public String getBy(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}
	
	public Set<String> getKeys(String patternKey) {
		return stringRedisTemplate.keys(patternKey);
	}
	
	public Set<String> getAllValuesBy(String patternKey) {
		final Set<String> keys = getKeys(patternKey);
		final Set<String> values = new HashSet<String>(keys.size());
		
		for (String key : keys) {
			values.add(getBy(key));
		}
		
		return values;
	}

	public void delete(String key) {
		stringRedisTemplate.opsForValue().getOperations().delete(key);
	}
	
	public void flushDb() {
		stringRedisTemplate.getConnectionFactory().getConnection().flushDb();
	}

}
