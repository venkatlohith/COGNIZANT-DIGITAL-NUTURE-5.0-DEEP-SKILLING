package com.example.orderservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

// Alternative to OpenFeign, as allowed by the exercise requirements
// ("communicate using WebClient (Spring WebFlux) OR OpenFeign").
// This bean is provided for reference / can be swapped in for UserClient.
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient userServiceWebClient(@Value("${user-service.url}") String userServiceUrl) {
        return WebClient.builder()
                .baseUrl(userServiceUrl)
                .build();
    }
}
