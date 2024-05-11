package com.example.demo.observer_reservations;

public interface ReservationState {
    public void addObserver(Observer obs);
    public void removeObserver(Observer obs);
    public void notifyObservers();
    
} 

