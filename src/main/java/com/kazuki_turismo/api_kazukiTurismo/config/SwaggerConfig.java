package com.kazuki_turismo.api_kazukiTurismo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Kazuki Turismo")
                        .version("1.0")
                        .description("Documentación del servicio web REST para el proyecto Kazuki Turismo de Desarrollo de Software"));
    }
}