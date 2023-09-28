package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableRetry
public class SemicalorieApplication {
	public static void main(String[] args) {
		SpringApplication.run(SemicalorieApplication.class, args);
	}

}
