package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com")
@EnableJpaRepositories(basePackages = "com.repository")
@EntityScan(basePackages = "com.bean")
public class Phase3ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Phase3ProjectApplication.class, args);
		System.out.println("Phase 3 Project Starting !!!");
	}

}
