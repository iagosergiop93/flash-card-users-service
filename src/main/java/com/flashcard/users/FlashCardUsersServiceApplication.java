package com.flashcard.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.flashcard.users.repositories")
public class FlashCardUsersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashCardUsersServiceApplication.class, args);
	}

}
