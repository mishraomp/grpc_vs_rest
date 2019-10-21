package com.om.example.springreactivemongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableMongoAuditing
@EnableReactiveMongoRepositories
@SpringBootApplication
@EnableWebFlux
public class SpringReactiveMongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveMongoDbApplication.class, args);
	}

}
