package com.example.demo.service;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.opencsv.CSVWriter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Page<Transaction> getAllTransactions(Pageable page) {
        return transactionRepository.findAll(page);
    }

    public List<Transaction> getTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atStartOfDay().plusDays(1).minusNanos(1)); // End of the day
                                                                                                      // of endDate

        return transactionRepository.findAllByCreatedAtBetween(startTimestamp, endTimestamp);
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

  

}
