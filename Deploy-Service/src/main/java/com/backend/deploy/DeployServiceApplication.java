package com.backend.deploy;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DeployServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeployServiceApplication.class, args);
	}

}

@Component
class QueueListener implements ApplicationRunner {

	private final RedisTemplate<String, String> redisTemplate;

	public QueueListener(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// TODO - is multi-threading needed here?

		while (true) {
			String element = redisTemplate.opsForList().rightPop("uploaded-queue");
			if (element != null) {
				System.out.println("Received element from Redis queue: " + element);
			}
			try {
				Thread.sleep(250); // Sleep for 250 milliseconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
