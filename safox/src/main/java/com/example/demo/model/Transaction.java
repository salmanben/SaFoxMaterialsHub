package com.example.demo.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;

@Entity(name="transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String transaction_id;
    private double montant;
    private String method;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created_at;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private User client;

    public Transaction() {
    }

    public Transaction(String transaction_id, double montant, String method, User client) {
        this.transaction_id = transaction_id;
        this.montant = montant;
        this.method = method;
        this.client = client;
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

   

    public int getId() {
        return id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public double getMontant() {
        return montant;
    }

    public String getMethod() {
        return method;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public User getClient() {
        return client;
    }






    
}
