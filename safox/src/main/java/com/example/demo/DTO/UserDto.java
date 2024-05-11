package com.example.demo.DTO;

import java.sql.Timestamp;
import com.example.demo.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public class UserDto {
    
    
    private int id;
    // Required fields
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 250, message = "Name must be greater than 3 characters")
    private String name;
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+[0-9]{7,20}$", message = "Invalid phone number format: +1234567890")
    private String phone;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min=5, max=20, message = "Password must be between 5 and 20 characters")
    private String password;
    @Size(max=255, message = "Address must be less than 250 characters")
    @NotBlank(message = "Address is required")
    private String address;

    // Optional fields
    @Size(max=255, message = "Company name must be less than 250 characters")
    private String company_name;
    private boolean status;
    private Timestamp created_at;
    private Role role;


    public UserDto() {
    }

    public UserDto(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public UserDto(int id, String name, String phone, String email, String address, String company_name) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.company_name = company_name;
    }

    public UserDto(int id, String name, String phone, String email, String password, String address, String company_name,
            boolean status, Timestamp created_at, Role role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.company_name = company_name;
        this.status = status;
        this.created_at = created_at;
        this.role = role;
    }
  


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getCompany_name() {
        return company_name;
    }

    public boolean getStatus()
    {
        return status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Role getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto [address=" + address + ", company_name=" + company_name + ", created_at=" + created_at
                + ", email=" + email + ", id=" + id + ", name=" + name + ", password=" + password + ", phone=" + phone
                + ", role=" + role + ", status=" + status + "]";
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setRole(Role role) {
        this.role = role;
    }





} 
