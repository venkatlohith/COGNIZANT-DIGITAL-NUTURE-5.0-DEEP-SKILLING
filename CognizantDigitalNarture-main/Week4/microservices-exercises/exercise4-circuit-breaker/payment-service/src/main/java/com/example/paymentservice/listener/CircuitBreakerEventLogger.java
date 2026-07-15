package com.example.paymentservice.listener;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Subscribes to circuit breaker state-transition and call events so they
 * are logged (and can be shipped to a monitoring system, e.g. via the
 * "monitor fallback events" requirement of the exercise).
 */
@Component
@RequiredArgsConstructor
public class CircuitBreakerEventLogger {

    private static final Logger log = LoggerFactory.getLogger(CircuitBreakerEventLogger.class);

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @PostConstruct
    public void registerListeners() {
        CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("thirdPartyPayment");

        cb.getEventPublisher()
                .onStateTransition(event ->
                        log.warn("CircuitBreaker '{}' state transition: {} -> {}",
                                event.getCircuitBreakerName(),
                                event.getStateTransition().getFromState(),
                                event.getStateTransition().getToState()))
                .onError(event ->
                        log.error("CircuitBreaker '{}' recorded an error: {}",
                                event.getCircuitBreakerName(), event.getThrowable().toString()))
                .onSlowCallRateExceeded(event ->
                        log.warn("CircuitBreaker '{}' slow call rate exceeded: {}%",
                                event.getCircuitBreakerName(), event.getSlowCallRate()))
                .onFailureRateExceeded(event ->
                        log.warn("CircuitBreaker '{}' failure rate exceeded: {}%",
                                event.getCircuitBreakerName(), event.getFailureRate()))
                .onCallNotPermitted(event ->
                        log.warn("CircuitBreaker '{}' call NOT PERMITTED (circuit OPEN) - fallback will be used",
                                event.getCircuitBreakerName()));
    }
}
