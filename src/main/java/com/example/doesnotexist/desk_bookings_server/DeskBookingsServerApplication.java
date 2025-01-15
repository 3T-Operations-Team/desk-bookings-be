package com.example.doesnotexist.desk_bookings_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableMongoRepositories
public class DeskBookingsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeskBookingsServerApplication.class, args);
	}

}
