package in.ashokit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetRestController {
	@Autowired
	private WelcomeFeighClient client;
	
	@GetMapping("/greet")
	public String getGreetMsg() {
		
		String msg = "Good Morning..!!";
		String msg2 = client.getWelcomeMsg();
		return msg+msg2;
		
	}
}
