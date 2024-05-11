package com.example.demo.DTO;

public class PaymentRequestDto {
    
    private double amount;
    private String token;
    private String currency;

    public PaymentRequestDto() {
    }

  
    public PaymentRequestDto(double amount, String token, String currency) {
        this.amount = amount;
        this.token = token;
        this.currency = currency;
    }



    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
