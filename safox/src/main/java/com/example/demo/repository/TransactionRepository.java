package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.sql.Timestamp;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM transactions t WHERE t.created_at BETWEEN :startDate AND :endDate")
    List<Transaction> findAllByCreatedAtBetween(Timestamp startDate, Timestamp endDate);


    Page<Transaction> findAll(Pageable pageable);
}
