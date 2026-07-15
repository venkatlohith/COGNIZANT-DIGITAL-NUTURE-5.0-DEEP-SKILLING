package com.example.paymentservice.service;

import com.example.paymentservice.client.ThirdPartyPaymentClient;
import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.dto.PaymentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final ThirdPartyPaymentClient thirdPartyPaymentClient;

    /**
     * Calls the slow/unreliable third-party payment API, guarded by:
     *  - @TimeLimiter: gives up waiting after the configured timeout (3s)
     *  - @CircuitBreaker: trips OPEN after too many failures/slow calls,
     *    at which point calls short-circuit straight to the fallback
     *    without even attempting the network call.
     *
     * Because @TimeLimiter requires an async return type, the blocking
     * RestTemplate call is wrapped in a CompletableFuture.
     */
    @CircuitBreaker(name = "thirdPartyPayment", fallbackMethod = "chargeFallback")
    @TimeLimiter(name = "thirdPartyPayment")
    public CompletableFuture<PaymentResponse> charge(PaymentRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Attempting to charge order {} via third-party API", request.getOrderId());
            return thirdPartyPaymentClient.charge(request);
        }, Executors.newVirtualThreadPerTaskExecutor());
    }

    /**
     * Fallback invoked when the circuit is OPEN, the call times out, or
     * the third-party API throws an exception. Signature must match the
     * original method plus a trailing Throwable parameter.
     */
    public CompletableFuture<PaymentResponse> chargeFallback(PaymentRequest request, Throwable throwable) {
        log.error("Fallback triggered for order {}. Reason: {}", request.getOrderId(), throwable.toString());

        // Business decision: queue the payment for later retry/reconciliation
        // instead of failing the user's request outright.
        PaymentResponse response = new PaymentResponse(
                request.getOrderId(),
                "PENDING_FALLBACK",
                "Payment provider is currently unavailable. Your payment has been queued and will be retried automatically."
        );
        return CompletableFuture.completedFuture(response);
    }
}
