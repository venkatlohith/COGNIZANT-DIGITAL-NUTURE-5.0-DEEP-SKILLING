package com.example.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String orderId;
    private String status;      // SUCCESS, FAILED, PENDING_FALLBACK
    private String message;
}
