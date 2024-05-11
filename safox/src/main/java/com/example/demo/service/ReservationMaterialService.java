package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.ReservationMaterial;
import com.example.demo.repository.ReservationMaterialRepository;

@Service
public class ReservationMaterialService {
    

    private final ReservationMaterialRepository reservationMaterialRepository;

    public ReservationMaterialService(ReservationMaterialRepository reservationMaterialRepository) {
        this.reservationMaterialRepository = reservationMaterialRepository;
    }

    public List<ReservationMaterial> findByReservationId(int reservationId) {
        return reservationMaterialRepository.findByReservationId(reservationId);
    }
    
    public List<ReservationMaterial> findByMaterialId(int materialId) {
        return reservationMaterialRepository.findByMaterialId(materialId);
    }


    public void save(ReservationMaterial reservationMaterial) {
        reservationMaterialRepository.save(reservationMaterial);
    }




}
