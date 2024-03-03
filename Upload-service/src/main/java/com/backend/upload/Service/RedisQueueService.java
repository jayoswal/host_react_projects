package com.backend.upload.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisQueueService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void publish(String id) {
		redisTemplate.opsForList().leftPush("uploaded-queue", id);
	}

}
