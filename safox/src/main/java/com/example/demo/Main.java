package com.example.demo;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

public class Main {
    public static void main(String[] args) {
        int i = LocalDate.now().getMonthValue();
        System.out.println(i);
    }
}
