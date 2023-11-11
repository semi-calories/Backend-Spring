package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;
import java.util.TimeZone;

@EnableScheduling // 스케줄링 기능을 enable 함
@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableRetry
public class SemicalorieApplication {
	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
	public static void main(String[] args) {
		SpringApplication.run(SemicalorieApplication.class, args);
	}
}
