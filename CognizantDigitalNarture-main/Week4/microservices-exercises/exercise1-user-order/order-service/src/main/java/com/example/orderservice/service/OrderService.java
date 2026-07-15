package com.example.orderservice.service;

import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.UserDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient; // OpenFeign client -> user-service

    public Order createOrder(Order order) {
        // Validate that the user exists before creating the order.
        // Throws if user-service returns 404, propagated via Feign.
        try {
            userClient.getUserById(order.getUserId());
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RuntimeException("Cannot create order: user " + order.getUserId() + " does not exist");
        }
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Combines order + user info in a single response (enrichment pattern)
    public Map<String, Object> getOrderWithUserDetails(Long orderId) {
        Order order = getOrderById(orderId);
        UserDto user = userClient.getUserById(order.getUserId());
        return Map.of(
                "order", order,
                "user", user
        );
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
