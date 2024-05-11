package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.example.demo.model.Reservation;
import com.example.demo.model.User;
import com.example.demo.repository.ReservationRepository;

@Service
public class ReservationService {
   
    
    private final ReservationRepository reservationRepository;
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllTreatedReservations() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return reservationRepository.findByStatusNot("pending", sort);
    }

    public List<Reservation> getAllNotTreatedReservations() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return reservationRepository.findByStatus("pending", sort);
    }

    public Reservation getReservation(int id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public void updateReservationStatus(int id, String status) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation != null) {
            reservation.setStatus(status);
            reservationRepository.save(reservation);
        }
    }
    

    public Reservation saveReservation(Reservation reservation) {
        Reservation savedReservation = reservationRepository.save(reservation);
        return savedReservation;
    }

    public double getTotal(User client) {
      return reservationRepository.getTotal(client);

    }

    public List<Reservation> getReservationsByClient(User client) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return reservationRepository.findByClient(client, sort);
    }
    public Page<Reservation> getReservationsByClient(User client, Pageable page) {
        return reservationRepository.findByClient(client, page);
    }


    public List<Reservation> findActiveReservationsOfADate(Date date) {
        return reservationRepository.findActiveReservationsOfADate(date);
    }

    public Page<Reservation> getAllTreatedReservations(Pageable page) {
        return reservationRepository.findByStatusNot("pending", page);
    }

    public Page<Reservation> getAllNotTreatedReservations(Pageable page) {
        return reservationRepository.findByStatus("pending", page);
    }





}
