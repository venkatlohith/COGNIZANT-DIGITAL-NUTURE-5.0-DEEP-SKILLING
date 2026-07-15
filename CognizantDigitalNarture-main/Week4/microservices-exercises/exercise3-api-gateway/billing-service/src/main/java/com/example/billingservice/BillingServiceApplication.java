package com.example.billingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
public class BillingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
}

@RestController
@RequestMapping("/billing")
class BillingController {

    @GetMapping("/{id}")
    public Map<String, Object> getInvoice(@PathVariable String id) {
        return Map.of(
                "invoiceId", id,
                "amount", 199.99,
                "servedBy", "billing-service"
        );
    }

    @GetMapping
    public Map<String, Object> ping() {
        return Map.of("service", "billing-service", "status", "UP");
    }
}
