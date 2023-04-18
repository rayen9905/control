package com.pfe.control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pfe.repos","com.pfe.auth","com.pfe.config","com.pfe.demo","com.pfe.controller","com.pfe.CrossConfig","com.pfe.socket","com.pfe.socket1"})
@EntityScan(basePackages = {"com.pfe.entities"})
@EnableJpaRepositories("com.pfe.repos")
@EnableConfigurationProperties
@EnableWebSocket
public class ControlAccessApplication {
	public static void main(String[] args) {

		SpringApplication.run(ControlAccessApplication.class, args);

	}
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
