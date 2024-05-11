package com.example.demo.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class EmailSettingsDto {
    
    private int id;
    @NotEmpty(message = "Host is required")
    @Size(max=255, message = "Host is too long")
    private String host;
    @Digits(integer = 5, fraction = 0, message = "Port is not valid")
    private int port;
    @NotEmpty(message = "Username is required")
    @Size(max=255, message = "Username is too long")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(max=255, message = "Password is too long")
    private String password;
    @NotEmpty(message = "Encryption is required")
    @Size(max=255, message = "Encryption is too long")
    private String encryption;


    public EmailSettingsDto(String host, int port, String username, String password, String encryption) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.encryption = encryption;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }
}
