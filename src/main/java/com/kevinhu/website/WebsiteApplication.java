package com.kevinhu.website;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class WebsiteApplication {

	@Bean // still don't really understand
	public ModelMapper modelMapper() {
		ModelMapper mm = new ModelMapper();
		return mm;
		// to be used for mapping later
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);
		// the main method that actually runs first
	}

	
}
