package org.thermoweb.rpg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RpgWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpgWebApplication.class, args);
    }
}