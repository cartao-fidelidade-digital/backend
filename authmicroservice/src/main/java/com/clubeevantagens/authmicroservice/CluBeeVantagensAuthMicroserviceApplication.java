package com.clubeevantagens.authmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class CluBeeVantagensAuthMicroserviceApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CluBeeVantagensAuthMicroserviceApplication.class, args);

		System.out.println("BACKEND ESTA LIGADO");
	}

	// CrossOrigin global
	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedMethods("*")
				.allowedHeaders("*")
				.allowedOrigins("http://localhost:5173", "http://localhost:8081","http://localhost:3000", "http://192.268.0.50:8081", "http://192.168.0.50:8081");


	}


}
