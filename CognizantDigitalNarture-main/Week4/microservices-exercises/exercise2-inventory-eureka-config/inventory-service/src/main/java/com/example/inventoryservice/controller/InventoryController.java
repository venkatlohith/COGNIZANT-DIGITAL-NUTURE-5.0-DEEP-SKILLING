package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        return new ResponseEntity<>(inventoryService.createInventory(inventory), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Map<String, Object>> getStockDetails(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getStockDetails(productId));
    }

    @PatchMapping("/product/{productId}/adjust")
    public ResponseEntity<Inventory> adjustStock(@PathVariable Long productId, @RequestParam Integer delta) {
        return ResponseEntity.ok(inventoryService.adjustStock(productId, delta));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Inventory>> getLowStockItems(@RequestParam(defaultValue = "10") Integer threshold) {
        return ResponseEntity.ok(inventoryService.getLowStockItems(threshold));
    }
}
