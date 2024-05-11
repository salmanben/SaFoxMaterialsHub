package com.example.demo.observer_reservations;

import org.springframework.stereotype.Component;

@Component
public interface Observer {
    
    public void update(ReservationState obs);
}
