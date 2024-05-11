package com.example.demo.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class StripeSettingsDto {
 
    private int id;
    @NotEmpty(message = "Client ID is required")
    @Size(max=255, message = "Client ID is too long")
    private String clientId;
    @NotEmpty(message = "Client Secret is required")
    @Size(max=255, message = "Client Secret is too long")
    private String clientSecret;
    @NotEmpty(message = "Mode is required")
    @Size(max=255, message = "Mode is too long")
    private String mode;



    public StripeSettingsDto(String clientId, String clientSecret, String mode) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.mode = mode;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
