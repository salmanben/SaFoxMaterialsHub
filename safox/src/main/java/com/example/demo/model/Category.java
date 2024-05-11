package com.example.demo.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(unique = true)
    private String image;
    @Column(columnDefinition = "BIT DEFAULT 1")
    private boolean status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created_at;

    @OneToMany(mappedBy = "category", fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<Material> materials;

    public Category() {
    }

    public Category(String name, String description,  boolean status) {
        this.name = name;
        this.description = description;
        this.status = status;

    }

    public Category(String name, String description, String image, boolean status) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.status = status;
        created_at = new Timestamp(System.currentTimeMillis());

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

    public boolean getStatus() {
        return status;
    }


    public Timestamp getCreated_at() {
        return created_at;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
