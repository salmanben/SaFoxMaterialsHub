package com.example.demo.configuration;

import com.example.demo.service.StripeSettingsService;
import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    private final StripeSettingsService stripeSettingsService;

    public StripeConfig(StripeSettingsService stripeSettingsService) {
        this.stripeSettingsService = stripeSettingsService;
    }

    @PostConstruct
    public void setup() {
        String secretKey = stripeSettingsService.getStripeSettings().getClientSecret();
        Stripe.apiKey = secretKey;
    }
}

