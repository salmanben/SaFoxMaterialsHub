package com.example.demo.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SmsSettingsDto {

    private int id;
    @NotEmpty(message = "Account SID is required")
    @Size(max = 255, message = "Account SID is too long")
    private String accountSid;
    @NotEmpty(message = "Auth Token is required")
    @Size(max = 255, message = "Auth Token is too long")
    private String authToken;
    @NotEmpty(message = "From Number is required")
    @Size(max = 255, message = "From Number is too long")
    private String fromNumber;

    public SmsSettingsDto() {
    }

    public SmsSettingsDto(String accountSid, String authToken, String fromNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromNumber = fromNumber;
    }

    public int getId() {
        return id;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

}
