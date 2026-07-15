package com.example.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
public class CustomerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
}

// Simple downstream service the gateway will route to.
// Note the real path is "/customers/**"; the gateway rewrites
// external "/api/customers/**" requests down to this internal path.
@RestController
@RequestMapping("/customers")
class CustomerController {

    @GetMapping("/{id}")
    public Map<String, Object> getCustomer(@PathVariable String id) {
        return Map.of(
                "id", id,
                "name", "Customer " + id,
                "servedBy", "customer-service"
        );
    }

    @GetMapping
    public Map<String, Object> ping() {
        return Map.of("service", "customer-service", "status", "UP");
    }
}
