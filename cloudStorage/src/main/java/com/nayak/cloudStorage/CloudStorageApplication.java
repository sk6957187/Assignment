package com.nayak.cloudStorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CloudStorageApplication {

	public static void main(String[] args) {
		System.out.println("Application is running");
		SpringApplication.run(CloudStorageApplication.class, args);
	}

}
