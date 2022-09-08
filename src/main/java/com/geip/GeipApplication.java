package com.geip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GeipApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeipApplication.class, args);
	}

}
