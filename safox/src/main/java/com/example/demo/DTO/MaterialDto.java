package com.example.demo.DTO;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Category;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;

public class MaterialDto {

    private int id;
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Name must be greater than 3 characters")
    private String name;
    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;
    private MultipartFile image;
    @NotNull(message = "Price must not be empty")
    @Digits(integer = 10, fraction = 2, message = "Price by day must be a number")
    private double price_by_day;
    @NotNull(message = "Shipped price must not be empty")
    @Digits(integer = 10, fraction = 2, message = "Shipped price must be a number")
    private double shipped_price;
    @NotNull(message = "Stock must not be empty")
    @Digits(integer = 10, fraction = 0, message = "Stock must be a number")
   
    private int stock;
    private Category category;


    public MaterialDto() {
    }

    public MaterialDto(int id, String name, String description,  double price_by_day, double shipped_price, int stock, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price_by_day = price_by_day;
        this.shipped_price = shipped_price;
        this.stock = stock;
        this.category = category;
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

    public MultipartFile getImage() {
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

    public Category getCategory() {
        return category;
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

    public void setImage(MultipartFile image) {
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

    public void setCategory(Category category) {
        this.category = category;
    }
    


    @Override
    public String toString() {
        return "MaterialDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", price_by_day=" + price_by_day +
                ", shipped_price=" + shipped_price +
                ", stock=" + stock +
                ", category=" + category +
                '}';
    }

}
