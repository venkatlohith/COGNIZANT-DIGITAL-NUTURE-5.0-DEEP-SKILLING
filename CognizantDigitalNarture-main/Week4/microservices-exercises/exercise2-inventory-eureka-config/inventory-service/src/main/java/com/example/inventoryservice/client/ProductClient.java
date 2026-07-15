package com.example.inventoryservice.client;

import com.example.inventoryservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// "name" alone is resolved via Eureka service discovery -
// no hardcoded host/port needed, unlike exercise1's UserClient.
@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);

    @org.springframework.web.bind.annotation.PutMapping("/api/products/{id}/stock")
    ProductDto updateStock(@PathVariable("id") Long id, @RequestParam("stock") Integer stock);
}
