package com.example.demo.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;

public class LoginDto {
    
    @NotEmpty(message = "Email is required")
    @Email
    private String username;
    
    @NotEmpty(message = "Password is required")
    private String password;

    public LoginDto() {
    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
