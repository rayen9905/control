package com.pfe.control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pfe.repos","com.pfe.auth","com.pfe.config","com.pfe.demo","com.pfe.controller"})
@EntityScan(basePackages = {"com.pfe.entities"})
@EnableJpaRepositories("com.pfe.repos")
//@EnableConfigurationProperties
public class ControlAccessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlAccessApplication.class, args);
	}

}
