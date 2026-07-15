package com.example.resiliencegateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Resilience Configuration using Resilience4j Circuit Breaker
// Configures a default circuit breaker for all routes in the API Gateway
@Configuration
public class ResilienceConfiguration {

    // Default customizer applies to all circuit breakers unless overridden
    // CircuitBreakerConfig.ofDefaults():
    //   - slidingWindowSize = 100
    //   - failureRateThreshold = 50%
    //   - waitDurationInOpenState = 60 seconds
    // TimeLimiterConfig.ofDefaults():
    //   - timeoutDuration = 1 second
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.ofDefaults())
                .build());
    }
}
