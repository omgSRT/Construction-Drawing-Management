package com.GSU24SE43.ConstructionDrawingManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication()
public class ConstructionDrawingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConstructionDrawingManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner openSwaggerUI() {
		return args -> {
			String swaggerUIUrl = "http://localhost:8080/swagger-ui/index.html";
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + swaggerUIUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
	}
}
