package jje.ninedoggy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ninedoggyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ninedoggyApplication.class, args);
    }
}
