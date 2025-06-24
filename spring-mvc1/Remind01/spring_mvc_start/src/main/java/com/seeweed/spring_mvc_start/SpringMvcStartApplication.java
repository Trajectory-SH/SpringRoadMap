package com.seeweed.spring_mvc_start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringMvcStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcStartApplication.class, args);
	}

}
