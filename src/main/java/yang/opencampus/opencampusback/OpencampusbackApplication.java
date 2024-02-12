package yang.opencampus.opencampusback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableJpaRepositories
public class OpencampusbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpencampusbackApplication.class, args);
		System.out.println("beginning...");
	}

}
