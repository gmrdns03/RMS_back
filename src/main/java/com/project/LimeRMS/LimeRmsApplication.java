package com.project.LimeRMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.project.LimeRMS")
public class LimeRmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LimeRmsApplication.class, args);
	}

}
