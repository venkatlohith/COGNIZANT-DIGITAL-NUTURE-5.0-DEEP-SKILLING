package com.example.edgeservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// GlobalFilter applies to ALL routes defined in the gateway
// Logs every incoming request URI before forwarding to the downstream service
@Component
public class LoggingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Log the incoming request URI
        System.out.println("Request: " + exchange.getRequest().getURI());

        // Continue the filter chain (forward the request to the next filter/route)
        return chain.filter(exchange);
    }
}
