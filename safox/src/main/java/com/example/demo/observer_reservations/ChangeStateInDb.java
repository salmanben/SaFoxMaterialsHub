package com.example.demo.observer_reservations;

import org.springframework.stereotype.Component;

import com.example.demo.service.ReservationService;

@Component
public class ChangeStateInDb implements Observer{
    

    private final ReservationService reservationService;


    public ChangeStateInDb(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    
    @Override
    public void update(ReservationState obs) {
        reservationService.updateReservationStatus(((ReservationStateImpl)obs).getId(), ((ReservationStateImpl)obs).getState());
    }
}
