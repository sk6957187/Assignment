package com.nayak.Hellow.World.Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HellowWorldSpringApplication {

	public static void main(String[] args) {
		System.out.println("Start program....!!");
		SpringApplication.run(HellowWorldSpringApplication.class, args);
		System.out.println("Ended program....!!");
	}

}
