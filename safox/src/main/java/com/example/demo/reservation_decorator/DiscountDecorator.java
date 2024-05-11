package com.example.demo.reservation_decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.service.GeneralSettingsService;
import com.example.demo.service.ReservationService;

@Service
public class DiscountDecorator extends Decorator {

    private User client;
    private GeneralSettingsService generalSettingsService;
    private ReservationService reservationService;

    public DiscountDecorator() {
        super();
    }

    public DiscountDecorator(ReservationDecorator reservationDecorator, User client,
            GeneralSettingsService generalSettingsService, ReservationService reservationService) {
        super(reservationDecorator);
        this.client = client;
        this.generalSettingsService = generalSettingsService;
        this.reservationService = reservationService;
    }

    @Override
    public double getPrice() {
        double price = super.getPrice();
        double promotion_threshold = generalSettingsService.getGeneralSettings().getPromotion_threshold();
        double total = reservationService.getTotal(client);
        if (total >= promotion_threshold) {
            double discount = generalSettingsService.getGeneralSettings().getPromotion_discount();
            return price - price * discount / 100;
        }
        return price;
    }

}
