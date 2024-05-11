package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.example.demo.model.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer>{
    

    // find all materials by name containing or description containing
    @Query("SELECT m FROM materials m WHERE m.name LIKE %:keyword% OR m.description LIKE %:keyword%")
    List<Material> search(@Param("keyword") String keyword);

    // find all with pagination
    Page<Material> findAll(Pageable pageable);

}
