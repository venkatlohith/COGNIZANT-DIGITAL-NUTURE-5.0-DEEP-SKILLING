package com.example.inventoryservice.service;

import com.example.inventoryservice.client.ProductClient;
import com.example.inventoryservice.dto.ProductDto;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductClient productClient; // Feign client, resolved via Eureka

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    // Combines inventory record with live product details (name/price)
    public Map<String, Object> getStockDetails(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("No inventory record for product " + productId));
        ProductDto product = productClient.getProductById(productId);
        return Map.of(
                "product", product,
                "inventory", inventory
        );
    }

    // Adjusts stock and syncs the total back to product-service
    public Inventory adjustStock(Long productId, Integer delta) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("No inventory record for product " + productId));
        inventory.setQuantityOnHand(inventory.getQuantityOnHand() + delta);
        Inventory saved = inventoryRepository.save(inventory);
        productClient.updateStock(productId, saved.getQuantityOnHand());
        return saved;
    }

    public List<Inventory> getLowStockItems(Integer threshold) {
        return inventoryRepository.findByQuantityOnHandLessThan(threshold);
    }
}
