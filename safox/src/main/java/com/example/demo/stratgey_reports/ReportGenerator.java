package com.example.demo.stratgey_reports;
import java.io.IOException;

import com.example.demo.model.Transaction;
import java.util.List;

import org.springframework.http.ResponseEntity;
public interface ReportGenerator {
    
    ResponseEntity<byte[]> generateReport(List<Transaction> transactions, String name) throws IOException;
}
