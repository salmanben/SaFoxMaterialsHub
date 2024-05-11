package com.example.demo.observer_reservations;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ReservationStateImpl implements ReservationState{
    
    private List<Observer> observers = new ArrayList<Observer>();
    private int id;
    private String state;

    

    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update(this);
        }
    }

    public void setData(int id, String state){
        this.id = id;
        this.state = state;
        notifyObservers();
    }

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }


}
