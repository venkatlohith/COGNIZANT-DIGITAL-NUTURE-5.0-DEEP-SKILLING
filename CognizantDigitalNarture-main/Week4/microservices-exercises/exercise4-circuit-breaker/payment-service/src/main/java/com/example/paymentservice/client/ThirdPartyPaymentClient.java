package com.example.paymentservice.client;

import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Thin wrapper around the (slow / unreliable) third-party payment API.
 * Kept separate from PaymentService so the circuit breaker wraps only
 * the network call, not our own business logic.
 */
@Component
@RequiredArgsConstructor
public class ThirdPartyPaymentClient {

    private final RestTemplate restTemplate;

    @Value("${third-party.payment-api.url}")
    private String paymentApiUrl;

    public PaymentResponse charge(PaymentRequest request) {
        // In a real system this hits an external gateway (Stripe, etc).
        // Here it's deliberately pointed at a slow/unreachable URL so the
        // circuit breaker's timeout and failure-rate logic can be observed.
        return restTemplate.postForObject(paymentApiUrl, request, PaymentResponse.class);
    }
}
