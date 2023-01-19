package com.sparta.zipsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ZipsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipsaApplication.class, args);
	}

}
