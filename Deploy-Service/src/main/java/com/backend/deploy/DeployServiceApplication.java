package com.backend.deploy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.backend.deploy.service.BuildService;

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

	@Autowired
	private BuildService buildService;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// TODO - is multi-threading needed here?

		while (true) {
			String id = redisTemplate.opsForList().rightPop("uploaded-queue");
			if (id != null) {
				buildService.downloadAndBuildAndDeploy(id);
			}
			try {
				Thread.sleep(1000); // Sleep for 250 milliseconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
