package com.example.orderservice.service;

import com.example.orderservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

// Demonstrates the WebClient (reactive) approach to calling user-service,
// as an alternative to the OpenFeign-based UserClient used by OrderService.
@Service
@RequiredArgsConstructor
public class UserWebClientService {

    private final WebClient userServiceWebClient;

    public Mono<UserDto> getUserById(Long id) {
        return userServiceWebClient.get()
                .uri("/api/users/{id}", id)
                .retrieve()
                .bodyToMono(UserDto.class);
    }
}
