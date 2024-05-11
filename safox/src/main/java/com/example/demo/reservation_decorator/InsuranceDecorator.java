package com.example.demo.reservation_decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.service.GeneralSettingsService;

@Service
public class InsuranceDecorator extends Decorator{

    private double insurancePrice;
    private GeneralSettingsService generalSettingsService;
    

    public InsuranceDecorator(){
        super();
    }

    public InsuranceDecorator(ReservationDecorator reservationDecorator, GeneralSettingsService generalSettingsService) {
        super(reservationDecorator);
        this.generalSettingsService = generalSettingsService;
    }

    @Override
    public double getPrice() {
        double insurancePrice = generalSettingsService.getGeneralSettings().getInsurance_price();
        System.out.println("Insurance price: " + insurancePrice);
        return super.getPrice() + insurancePrice;
    }
    
}
