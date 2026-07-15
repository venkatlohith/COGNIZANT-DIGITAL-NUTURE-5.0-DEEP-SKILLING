package com.example.orderservice.client;

import com.example.orderservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// OpenFeign declarative REST client for user-service.
// "name" + "url" combo is used here since we are not using Eureka in this exercise.
// In exercise2 the "name" alone (resolved via Eureka) would be enough.
@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
}
