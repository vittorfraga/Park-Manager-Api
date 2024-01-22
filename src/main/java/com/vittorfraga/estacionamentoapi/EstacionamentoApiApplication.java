package com.vittorfraga.estacionamentoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class EstacionamentoApiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EstacionamentoApiApplication.class);
		//app.setAdditionalProfiles("test");
		app.run(args);


	}
}
