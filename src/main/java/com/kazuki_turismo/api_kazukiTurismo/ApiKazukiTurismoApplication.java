package com.kazuki_turismo.api_kazukiTurismo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.kazuki_turismo.api_kazukiTurismo")
public class ApiKazukiTurismoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiKazukiTurismoApplication.class, args);
    }
}