package com.example.demo.reservation_decorator;

import org.springframework.stereotype.Service;

@Service
public class Decorator implements ReservationDecorator{
    

    protected ReservationDecorator reservationDecorator;

    public Decorator() {
    }
    public Decorator(ReservationDecorator reservationDecorator) {
        this.reservationDecorator = reservationDecorator;
    }
    
    @Override
    public double getPrice() {
        return reservationDecorator.getPrice();
    }
}
