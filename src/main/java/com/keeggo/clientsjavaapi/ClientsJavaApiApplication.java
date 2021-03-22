package com.keeggo.clientsjavaapi;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

/**
 * Class that starts the application
 * 
 * @author Mariana Azevedo
 * @since 03/04/2020 
 */
@Log4j2
@SpringBootApplication
public class ClientsJavaApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ClientsJavaApiApplication.class, args);
		log.info("ClientsJavaAPI started successfully at {}", LocalDateTime.now());
	}

}
