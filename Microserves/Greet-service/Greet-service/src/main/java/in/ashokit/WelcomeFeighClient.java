package in.ashokit;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name= "WELCOME-SERVICE")
public interface WelcomeFeighClient {
	@GetMapping("/welcome")
	public String getWelcomeMsg();
}
