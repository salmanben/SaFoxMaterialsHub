package com.example.demo.model;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity(name = "materials")
public class Material {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    private double price_by_day;
    private double shipped_price;
    private int stock;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private  Timestamp created_at;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    @ManyToMany(mappedBy = "materials", fetch = FetchType.LAZY)
    private List<Reservation> reservations;


    public Material() {
    }
    public Material(String name, String description, String image, double price_by_day, double shipped_price, int stock, Category category) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price_by_day = price_by_day;
        this.shipped_price = shipped_price;
        this.stock = stock;
        this.category = category;
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public double getPrice_by_day() {
        return price_by_day;
    }

    public double getShipped_price() {
        return shipped_price;
    }
   
    public int getStock() {
        return stock;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Category getCategory() {
        return category;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice_by_day(double price_by_day) {
        this.price_by_day = price_by_day;
    }

    public void setShipped_price(double shipped_price) {
        this.shipped_price = shipped_price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }





}
