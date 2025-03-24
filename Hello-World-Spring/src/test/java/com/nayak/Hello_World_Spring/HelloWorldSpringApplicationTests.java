package com.nayak.Hello_World_Spring;

import static org.assertj.core.api.Assertions.assertThat;

import com.nayak.Hello_World_Spring.controller.HelloWorldResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloWorldSpringApplicationTests {

	@Autowired
	private HelloWorldResource helloWorldResource;

	// Test if the application context loads successfully
	@Test
	void contextLoads() {
		assertThat(helloWorldResource).isNotNull();
	}

	// Test if the API returns the expected message
	@Test
	void testHelloWorldMessage() {
		String message = helloWorldResource.getUserMessage();
		assertThat(message).contains("Configured Message:");
	}
}
