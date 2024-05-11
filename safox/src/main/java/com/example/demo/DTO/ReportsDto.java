package com.example.demo.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class ReportsDto {

    @NotEmpty(message = "Format is required")
    private String format;
    @NotNull(message = "startt Date is required")
    private LocalDate startDate;
    @NotEmpty(message = "End Date is required")
    private LocalDate endDate;

    public ReportsDto() {
    }

    public ReportsDto(String table, String format, LocalDate startDate, LocalDate endDate) {
        this.format = format;
        this.startDate = startDate;
        this.endDate = endDate;
    }



    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
