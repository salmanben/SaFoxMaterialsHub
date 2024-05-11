package com.example.demo.strategy_payment;

import com.example.demo.DTO.PaymentRequestDto;

public interface PaymentStrategy {
    String processPayment(PaymentRequestDto paymentRequestDto);
}