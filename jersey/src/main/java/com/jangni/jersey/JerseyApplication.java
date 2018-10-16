package com.jangni.jersey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JerseyApplication {

    @Bean
    public ApiApplication apiApplication(){
        return new ApiApplication();
    }


	public static void main(String[] args) {
		SpringApplication.run(JerseyApplication.class, args);
	}
}
