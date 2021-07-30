package com.jsalinas.redis.controller;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jsalinas.redis.repository.StringRedisRepository;
import com.jsalinas.redis.tweets.TweetIngestor;


@RestController
@RequestMapping("/api/redis")
public class RedisController {
	
	@Autowired
	private StringRedisRepository stringRedisRepository;
	
	@GetMapping
	public ResponseEntity<Set<String>> getAll() {
		
		final Set<String> tweets = stringRedisRepository.getAllValuesBy(TweetIngestor.TWEET_KEY + "*");
		
		for (String tweet : tweets) {
			System.out.println(tweet);
		}
		
		return ResponseEntity.ok(tweets);
	}
	
	@GetMapping("/keys")
	public ResponseEntity<Set<String>> getAllKeys() {
		return ResponseEntity.ok(stringRedisRepository.getKeys("*"));
	}
	
	@GetMapping("/value/{key}")
	public ResponseEntity<String> getValueByKey(@PathVariable String key) {
		return ResponseEntity.ok(stringRedisRepository.getBy(key));
	}
	
	@DeleteMapping("/{key}")
	public ResponseEntity<String> deleteByKey(@PathVariable String key) {
		stringRedisRepository.delete(key);
		return ResponseEntity.noContent().build();
	}
	
	
}
