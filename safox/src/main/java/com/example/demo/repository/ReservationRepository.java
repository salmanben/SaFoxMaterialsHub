package com.example.demo.repository;

import com.example.demo.model.Reservation;
import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByStatus(String status, Sort sort);
    Page<Reservation>  findByStatus(String status, Pageable pageable);

    List<Reservation> findByStatusNot(String status, Sort sort);
    Page<Reservation>  findByStatusNot(String status, Pageable pageable);



    @Query("SELECT COUNT(*) FROM reservations WHERE created_at > CURRENT_DATE")
    Long countTodayReservations();

    @Query("SELECT IFNULL(SUM(total), 0) FROM reservations WHERE created_at > CURRENT_DATE AND status = 'accepted'")
    Long todayEarnings();

    @Query("SELECT r FROM reservations r WHERE r.created_at > CURRENT_DATE")
    List<Reservation> todayReservations();

    @Query("SELECT COUNT(*) FROM reservations WHERE YEAR(created_at) = YEAR(CURRENT_DATE) AND MONTH(created_at) = :x")
    Long countMonthReservations(@Param("x") int x);

    @Query("SELECT IFNULL(SUM(total), 0) FROM reservations WHERE  YEAR(created_at) = YEAR(CURRENT_DATE) AND MONTH(created_at) = :x AND status = 'accepted'")
    Long monthEarnings(@Param("x") int x);


    @Query("SELECT r FROM reservations r  where YEAR(r.created_at) = YEAR(CURRENT_DATE) AND MONTH(r.created_at) = :x")
    List<Reservation> monthReservations(@Param("x") int x);

    
    @Query(value = "SELECT SUM(total) FROM reservations WHERE client = :x")
    public double getTotal(@Param("x") User x);

    List<Reservation> findByClient(User client, Sort sort);
    Page<Reservation> findByClient(User client, Pageable pageable);

    // get all materials where date end > the date x
    @Query("SELECT r FROM reservations r WHERE  r.status != 'refused' AND r.end_date > :x")
    List<Reservation> findActiveReservationsOfADate(@Param("x") Date x); 


}
