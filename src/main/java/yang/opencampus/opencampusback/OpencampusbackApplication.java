package yang.opencampus.opencampusback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class OpencampusbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpencampusbackApplication.class, args);
		System.out.println("beginning...");
	}

}
