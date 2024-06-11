package com.GSU24SE43.ConstructionDrawingManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication()
public class ConstructionDrawingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConstructionDrawingManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner openSwaggerUI() {
		return args -> {
			String swaggerUIUrl = "http://localhost:8080/swagger-ui/index.html";
			if (Desktop.isDesktopSupported()) {
				try {
					// Use the desktop class to open the URL
					Desktop desktop = Desktop.getDesktop();
					desktop.browse(new URI(swaggerUIUrl));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			} else {
				// Fallback method for unsupported desktop environments
				try {
					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + swaggerUIUrl);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
