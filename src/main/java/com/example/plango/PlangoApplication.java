package com.example.plango;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PlangoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlangoApplication.class, args);
    }

}
