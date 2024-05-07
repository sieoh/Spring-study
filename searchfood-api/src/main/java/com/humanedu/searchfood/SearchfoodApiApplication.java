package com.humanedu.searchfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SearchfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchfoodApiApplication.class, args);
	}

}
