package com.example.demo.reservation_decorator;

import com.example.demo.DTO.CartDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BasicPrice implements ReservationDecorator {



    private List<CartDto> cartDto;
    private int durree;

    public BasicPrice() {
    }
    public BasicPrice(List<CartDto> cartDto, int durree) {
        this.cartDto = cartDto;
        this.durree = durree;
    }

    @Override
    public double getPrice() {
        double price = 0;
        for (CartDto cart : cartDto) {
            
            price += cart.getMaterial().getPrice_by_day() * cart.getQuantity() * durree;
        }
        return price;
    }

}
