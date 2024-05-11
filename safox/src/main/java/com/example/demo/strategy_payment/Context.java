package com.example.demo.strategy_payment;

import com.example.demo.DTO.PaymentRequestDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Context {

    private final Map<String, PaymentStrategy> paymentStrategy;

    public Context(Map<String, PaymentStrategy> paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public PaymentStrategy getPaymentStrategy(String paymentType) {
        return paymentStrategy.get(paymentType);
    }
    


    public String executePayment(String paymentType, PaymentRequestDto paymentRequestDto) {
        PaymentStrategy paymentStrategy = getPaymentStrategy(paymentType);
        if (paymentStrategy != null) {
           return  paymentStrategy.processPayment(paymentRequestDto);
        } else {
            throw new IllegalArgumentException("Invalid payment type: " + paymentType);
        }
    }
}

