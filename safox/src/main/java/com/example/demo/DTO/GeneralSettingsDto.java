package com.example.demo.DTO;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class GeneralSettingsDto {

    private int id;
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is not valid")
    private String contact_email;
    @NotEmpty(message = "Phone is required")
    @Pattern(regexp = "^\\+[0-9]{7,20}$", message = "Invalid phone number")
    private String contact_phone;
    @NotNull(message = "assurance price is required")
    @Digits(integer = 5, fraction = 2, message = "assurance price is not valid")
    private double insurance_price;
    @Digits(integer = 5, fraction = 2, message = "promotion threshold  is not valid")
    private double promotion_threshold;
    @Digits(integer = 5, fraction = 2, message = "promotion percentage  is not valid")
    private double promotion_discount;





    public GeneralSettingsDto() {
    }

    public GeneralSettingsDto(String contact_email, String contact_phone, double insurance_price, double promotion_threshold, double promotion_discount) {

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
