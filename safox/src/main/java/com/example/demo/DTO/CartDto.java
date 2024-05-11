package com.example.demo.DTO;

import com.example.demo.model.Material;

public class CartDto {
    
    private Material material;
    private int quantity;

    public CartDto() {
    }

    public CartDto(Material material, int quantity) {
        this.material = material;
        this.quantity = quantity;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
