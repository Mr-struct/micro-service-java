package com.microservice.crud.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CrudEntitiesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudEntitiesServiceApplication.class, args);
	}

}