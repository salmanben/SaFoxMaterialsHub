package com.example.demo.service;

import com.example.demo.model.Reservation;
import com.example.demo.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    
    private final ReservationRepository reservationRepository;

    public DashboardService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long countTodayReservations(){
        return reservationRepository.countTodayReservations();
    }

   

    public long countTodayReservedMaterials(){
        List<Reservation> todReservations = reservationRepository.todayReservations();
        int count = 0;
        for(Reservation reservation : todReservations){
            count += reservation.getMaterials().size();
        }
        return count;
    }

    public long todayEarnings(){
        return reservationRepository.todayEarnings();
    }

    public Long[] yearReservations(){
        int lastMonth = LocalDate.now().getMonthValue();
        Long [] yearEarnings = new Long[12];
        for(int i = 1; i <= lastMonth; i++){
            yearEarnings[i-1] = reservationRepository.countMonthReservations(i);
        }
        for(int i = lastMonth ; i < 12; i++){
            yearEarnings[i] = 0L;
        }
        return yearEarnings;
    }

    public Long[] yearEarnings(){
        int lastMonth = LocalDate.now().getMonthValue();
        Long [] yearEarnings = new Long[12];
        for(int i = 1; i <= lastMonth; i++){
            yearEarnings[i-1] = reservationRepository.monthEarnings(i);
        }
        for(int i = lastMonth ; i < 12; i++){
            yearEarnings[i] = 0L;
        }
        return yearEarnings;
    }

    public Long[] yearReservedMaterials(){
        int lastMonth = LocalDate.now().getMonthValue();
        Long[] yearReservedMaterials = new Long[12];
        
        for(int i = 1; i <= lastMonth; i++){
            List<Reservation> monthReservations = reservationRepository.monthReservations(i);
            Long materialsCount = 0L;
            for(Reservation reservation : monthReservations){
                materialsCount += reservation.getMaterials().size();
            }
            yearReservedMaterials[i-1] = materialsCount;
        }
        
        for(int i = lastMonth; i < yearReservedMaterials.length; i++){
            yearReservedMaterials[i] = 0L;
        }
        
        return yearReservedMaterials;
    }

    
    

}
