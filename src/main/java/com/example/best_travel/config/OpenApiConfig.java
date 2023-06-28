package com.example.best_travel.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

//Url:http://localhost:8080/best_travel/swagger-ui/index.html

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Best Travel Api", 
    version = "1.0",
    description = "Documentation for endpoint in Best Travel"
    )
)
public class OpenApiConfig {
    
}
