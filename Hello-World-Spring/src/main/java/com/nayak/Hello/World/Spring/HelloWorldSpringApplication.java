package com.nayak.Hellow.World.Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorldSpringApplication {

	public static void main(String[] args) {
		System.out.println("Start program....!!");
		SpringApplication.run(HelloWorldSpringApplication.class, args);
		System.out.println("Ended program....!!");
	}

}
