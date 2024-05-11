package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.ReportsDto;
import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import com.example.demo.stratgey_reports.ReportGenerator;
import com.example.demo.stratgey_reports.ReportGeneratorContext;

@Controller
public class ReportsController {

    private final TransactionService transactionService;
    private final ReportGeneratorContext reportGeneratorContext;

    public ReportsController(TransactionService transactionService, ReportGeneratorContext reportGeneratorContext) {
        this.transactionService = transactionService;
        this.reportGeneratorContext = reportGeneratorContext;
    }

    @GetMapping("/admin/reports")
    public String index(@ModelAttribute("reportsDto") ReportsDto reportsDto) {
        return "admin/reports/index";
    }

    @PostMapping("/admin/reports/get")
    public ResponseEntity<byte[]> getReport(@ModelAttribute("reportsDto") ReportsDto reportsDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        List<Transaction> transactions = transactionService.getTransactionsByDate(reportsDto.getStartDate(), reportsDto.getEndDate());
          
        try {
            return reportGeneratorContext.executerGenerator(reportsDto.getFormat(), transactions, "transactions_report");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
