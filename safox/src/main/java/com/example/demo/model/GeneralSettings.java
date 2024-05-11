package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GeneralSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String contact_email;
    private String contact_phone;
    private double insurance_price;
    private double promotion_threshold;
    private double promotion_discount;

    public GeneralSettings() {
    }

    
    public GeneralSettings(String contact_email, String contact_phone, double insurance_price, double promotion_threshold, double promotion_discount) {

        this.contact_email = contact_email;
        this.contact_phone = contact_phone;
        this.insurance_price = insurance_price;
        this.promotion_threshold = promotion_threshold;
        this.promotion_discount = promotion_discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }




    public double getInsurance_price() {
        return insurance_price;
    }

    public void setInsurance_price(double insurance_price) {
        this.insurance_price = insurance_price;
    }

    public double getPromotion_threshold() {
        return promotion_threshold;
    }

    public void setPromotion_threshold(double promotion_threshold) {
        this.promotion_threshold = promotion_threshold;
    }

    public double getPromotion_discount() {
        return promotion_discount;
    }

    public void setPromotion_discount(double promotion_discount) {
        this.promotion_discount = promotion_discount;
    }
}
