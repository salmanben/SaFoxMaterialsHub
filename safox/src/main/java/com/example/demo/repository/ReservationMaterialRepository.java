package com.example.demo.repository;
import com.example.demo.model.ReservationMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationMaterialRepository  extends JpaRepository<ReservationMaterial, Integer>{
 
    List<ReservationMaterial> findByReservationId(int reservationId);
    List<ReservationMaterial> findByMaterialId(int materialId);
    
}
