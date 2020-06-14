package com.microservice.communservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CommunServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunServiceApplication.class, args);
	}

}
