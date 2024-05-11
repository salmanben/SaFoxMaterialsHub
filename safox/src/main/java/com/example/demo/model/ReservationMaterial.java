package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;

@Entity(name = "reservation_material")
public class ReservationMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "quantity")
    private int quantity;

    public ReservationMaterial() {
    }

    public ReservationMaterial(Reservation reservation, Material material, int quantity) {
        this.reservation = reservation;
        this.material = material;
        this.quantity = quantity;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Material getMaterial() {
        return material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

}
