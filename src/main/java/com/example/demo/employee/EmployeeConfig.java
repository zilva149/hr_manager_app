
package com.example.demo.employee;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfig {
	
	@Bean
	CommandLineRunner commandLineRunner(EmployeeRepository repository) {
		return args -> {
				Employee tom = new Employee(
						 "Tomas T.",
						 1L,
						 2L,
						 "Back End Developer",
						 25000d						 
				);
				
				Employee eve = new Employee(
						 "Ieva I.",
						 2L,
						 2L,
						 "Front End Developer",
						 22000d						 
				);
				
				repository.saveAll(
						List.of(tom, eve)
				);
		};
	};
	
}

