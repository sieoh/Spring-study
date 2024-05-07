package org.zerock;

import jakarta.servlet.http.HttpSessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.zerock.config.listener.SessionListener;

@SpringBootApplication
public class Ex02Application {
	public static void main(String[] args) {
		SpringApplication.run(Ex02Application.class, args);
	}

	@Bean
	public HttpSessionListener httpSessionListener() {
		return new SessionListener();
	}
}
