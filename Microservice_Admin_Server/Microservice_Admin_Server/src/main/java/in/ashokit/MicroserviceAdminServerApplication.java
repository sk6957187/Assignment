package in.ashokit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class MicroserviceAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceAdminServerApplication.class, args);
	}

}
