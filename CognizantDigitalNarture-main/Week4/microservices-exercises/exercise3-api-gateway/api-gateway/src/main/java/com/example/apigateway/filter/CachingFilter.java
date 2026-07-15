package com.example.apigateway.filter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * A simple, in-memory response cache for the gateway.
 *
 * Caches successful GET responses for a short TTL, keyed by the full
 * request path + query string, avoiding repeated downstream calls for
 * frequently requested, rarely-changing data (e.g. GET /api/customers/1).
 *
 * This is intentionally lightweight for the exercise; a production setup
 * would typically use Redis so the cache is shared across gateway instances.
 */
@Component
public class CachingFilter implements GlobalFilter, Ordered {

    private final Cache<String, CachedResponse> cache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofSeconds(30))
            .build();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!exchange.getRequest().getMethod().matches("GET")) {
            return chain.filter(exchange); // only cache idempotent GETs
        }

        String cacheKey = exchange.getRequest().getURI().toString();
        CachedResponse cached = cache.getIfPresent(cacheKey);

        if (cached != null) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().add("X-Cache", "HIT");
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, cached.contentType());
            DataBuffer buffer = response.bufferFactory().wrap(cached.body().getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }

        ServerHttpResponse originalResponse = exchange.getResponse();
        ServerHttpResponse decoratedResponse = new org.springframework.http.server.reactive.ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(org.reactivestreams.Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        DataBufferUtils.release(dataBuffer);
                        String bodyStr = new String(content, StandardCharsets.UTF_8);

                        if (getStatusCode() != null && getStatusCode().is2xxSuccessful()) {
                            String contentType = getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
                            cache.put(cacheKey, new CachedResponse(bodyStr, contentType != null ? contentType : "application/json"));
                        }

                        getHeaders().add("X-Cache", "MISS");
                        return getDelegate().bufferFactory().wrap(bodyStr.getBytes(StandardCharsets.UTF_8));
                    }));
                }
                return super.writeWith(body);
            }
        };

        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1; // run early, right after routing
    }

    private record CachedResponse(String body, String contentType) {
    }
}
