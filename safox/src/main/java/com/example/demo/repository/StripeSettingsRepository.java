package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.StripeSettings;
public interface StripeSettingsRepository  extends JpaRepository<StripeSettings, Integer>{
    
}
