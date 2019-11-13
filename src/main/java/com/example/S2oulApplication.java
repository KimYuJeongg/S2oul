package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class S2oulApplication {

	public static void main(String[] args) {
		SpringApplication.run(S2oulApplication.class, args);
	}	
}