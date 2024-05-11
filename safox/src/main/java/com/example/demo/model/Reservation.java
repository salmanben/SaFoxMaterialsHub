package com.example.demo.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double total;
    private double subtotal;
    private double discount;
    private Date start_date;
    private Date end_date;
    private boolean insurance;
    private double shipping_cost;
    private String shipping_address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private User client;
    @Column(columnDefinition = "VARCHAR(50) DEFAULT 'PENDING'", length = 50)
    private String status;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created_at;
    @ManyToMany
    @JoinTable(name = "reservation_material", joinColumns = @JoinColumn(name = "reservation_id"), inverseJoinColumns = @JoinColumn(name = "material_id"))
    
    private List<Material> materials;

    public Reservation() {
    }

    public Reservation(double total, double subtotal, double discount,  Date start_date, Date end_date,double shipping_cost, String shipping_address, boolean insurance, User client) {
        this.total = total;
        this.subtotal = subtotal;
        this.discount = discount;
        this.start_date = start_date;
        this.end_date = end_date;
        this.insurance = insurance;
        this.client = client;
        this.status = "pending";
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.shipping_cost = shipping_cost;
        this.shipping_address = shipping_address;

    }

    public int getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public boolean getInsurance() {
        return insurance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public User getClient() {
        return client;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public double getShipping_cost() {
        return shipping_cost;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_cost(double shipping_cost) {
        this.shipping_cost = shipping_cost;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    

}