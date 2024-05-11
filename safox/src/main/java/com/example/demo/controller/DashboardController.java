package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.DashboardService;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin/dashboard")
    public String index() {
        return "admin/dashboard/index";
    }

    @GetMapping("/admin/dashboard/today-statistics")
    public ResponseEntity<Long[]> todayStatistics() {
        Long countTodayReservations = dashboardService.countTodayReservations();
        Long countTodayReservedMaterials = dashboardService.countTodayReservedMaterials();
        Long todayEarnings = dashboardService.todayEarnings();
        Long data[] = {countTodayReservations,countTodayReservedMaterials, todayEarnings };
        return ResponseEntity.ok(data);
    }

    @GetMapping("/admin/dashboard/year-statistics")
    public ResponseEntity<Long[][]> yearStatistics() {
        Long [] yearReservations = dashboardService.yearReservations();
        Long [] yearEarnings = dashboardService.yearEarnings();
        Long[] yearReservedMaterials = dashboardService.yearReservedMaterials();

        Long data[][] = {yearEarnings, yearReservations,  yearReservedMaterials};
        return ResponseEntity.ok(data);
    }
}
