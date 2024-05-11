package com.example.demo.strategy_payment;

import com.example.demo.DTO.PaymentRequestDto;
import com.stripe.model.Charge;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class Stripe implements PaymentStrategy {

    public String processPayment(PaymentRequestDto request) {
        try {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount",(int) (request.getAmount() * 100));
            chargeParams.put("currency", request.getCurrency());
            chargeParams.put("source", request.getToken());
            Charge charge = Charge.create(chargeParams);
            return charge.getId();
        } catch (Exception e) {
            System.out.println("Stripe payment has been failed" + e.getMessage());
            return null;
        }
    }
}