package com.example.demo.reservation_decorator;
import com.example.demo.DTO.CartDto;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ShippingDecorator extends Decorator{

    private List<CartDto> cartDto;

    public ShippingDecorator(){
        super();
    }
    public ShippingDecorator(ReservationDecorator reservationDecorator, List<CartDto> cartDto) {
        super(reservationDecorator);
        this.cartDto = cartDto;
    }
    

    @Override
    public double getPrice() {
        double price = 0;
        for (CartDto cart : cartDto) {
            
            price += cart.getMaterial().getShipped_price() * cart.getQuantity();
        }
        return  price + super.getPrice();
    }

}
