package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.model.Transaction;

import java.util.List;
import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;

@Controller
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/admin/transactions")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Transaction> transactions = transactionService.getAllTransactions(pageable);
        List<Transaction> fetchedTransactions = transactions.getContent();
        model.addAttribute("transactions", fetchedTransactions);
        int totalPages = transactions.getTotalPages();
        System.out.println("totalPages: " + totalPages);
        model.addAttribute("totalPages", totalPages);
        int currentPage = transactions.getNumber();
        model.addAttribute("currentPage", currentPage);
        return "admin/transactions/index";
    }
}
