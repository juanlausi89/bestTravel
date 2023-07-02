package com.example.best_travel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value(value = "${api.base.url}")
    private String baseUrl;
    @Value(value = "${api.api-key}")
    private String apiKey;
    @Value(value = "${api.api-key.header}")
    private String apiKeyHeader;

    /*Si necesito utilizar varios Web client o varias base de datos tengo que usar la anotacion @Qualifier
     * Video 102 de la seccion spring web client 
     * https://www.udemy.com/course/spring-boot-3-y-spring-framework-6-2023/learn/lecture/37039906#overview
    */

    @Bean
    public WebClient currencyWebClient() {
        return WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(apiKeyHeader, apiKey)
                .build();
    }

    
}
