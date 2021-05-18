package wolox.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring main Book application
 */
@EnableJpaRepositories(value = "wolox.training.repositories")
@EntityScan("wolox.training.models")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TrainingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainingApplication.class, args);
    }

}
