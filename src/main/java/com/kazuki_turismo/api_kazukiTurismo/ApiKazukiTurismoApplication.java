package com.kazuki_turismo.api_kazukiTurismo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApiKazukiTurismoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiKazukiTurismoApplication.class, args);
	}

}