package anagrafica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(
        basePackages = "anagrafica.repository.audit"
)
public class StartAnagraficaMS {
	public static void main(String[] args) {
	    SpringApplication.run(StartAnagraficaMS.class, args);
	  }
}
