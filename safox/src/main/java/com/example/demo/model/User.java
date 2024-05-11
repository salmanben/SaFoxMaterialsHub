package com.example.demo.model;

import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // Required fields
    private String name;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50) DEFAULT 'CLIENT'")
    private Role role;

    // Optional fields
    private String company_name;
    @Column(columnDefinition = "BIT DEFAULT 1")
    private boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created_at;

    @OneToMany(mappedBy = "client")
    @JsonManagedReference
    private List<Reservation> reservations;



    @OneToMany(mappedBy = "client")
    @JsonManagedReference
    private List<Transaction> transactions;

    public User() {
    }



    public User(UserBuilder builder) {
        this.name = builder.name;
        this.phone = builder.phone;
        this.email = builder.email;
        this.password = builder.password;
        this.address = builder.address;
        this.company_name = builder.company_name;
        this.status = builder.status;
        this.created_at = builder.created_at;
        this.role = builder.role;
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

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

   

    public List<Transaction> getTransactions() {
        return transactions;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    
    
    // Builder class

    public static class UserBuilder {

        // Required fields
        private String name;
        private String phone;
        private String email;
        private String password;
        private String address;
        private Role role;

        // Optional fields
        private String company_name;
        private boolean status;
        private Timestamp created_at;

        public UserBuilder(String name, String phone, String email, String password, String address, Role role) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.password = password;
            this.address = address;
            this.role = role;
        }


        public UserBuilder company_name(String company_name) {
            this.company_name = company_name;
            return this;
        }

        public UserBuilder status(boolean status) {
            this.status = status;
            return this;
        }

        public UserBuilder created_at(Timestamp created_at) {
            this.created_at = created_at;
            return this;
        }


        public User build() {
            return new User(this);
        }

    }
}
