package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.observer_reservations.Observer;
import com.example.demo.model.Reservation;
import com.example.demo.model.ReservationMaterial;
import com.example.demo.observer_reservations.ReservationStateImpl;
import com.example.demo.service.GeneralSettingsService;
import com.example.demo.service.ReservationMaterialService;
import com.example.demo.service.ReservationService;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationStateImpl reservationState;
    private final Observer changeStateInDb;
    private final Observer notifyViaSms;
    private final Observer notifyViaEmail;
    @Autowired
    private GeneralSettingsService generalSettingsService;
    @Autowired
    ReservationMaterialService reservationMaterialService;

   
    public ReservationController(Observer changeStateInDb, Observer notifyViaSms, Observer notifyViaEmail, ReservationStateImpl reservationState, ReservationService reservationService) {
        this.changeStateInDb = changeStateInDb;
        this.notifyViaSms = notifyViaSms;
        this.notifyViaEmail = notifyViaEmail;
        this.reservationState = reservationState;
        this.reservationService = reservationService;
    }


    @GetMapping("/admin/reservations")
    public String reservations(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Reservation> reservations = reservationService.getAllNotTreatedReservations(pageable);
        List<Reservation> fetchedReservations = reservations.getContent();
        model.addAttribute("reservations", fetchedReservations);
        int totalPages = reservations.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        int currentPage = reservations.getNumber();
        model.addAttribute("currentPage", currentPage);
        return "admin/reservations/index";
    }

    @GetMapping("/admin/reservations/treated")
    public String trated_reservations(Model model,  @RequestParam(defaultValue = "0") int page) {
        // pagination with sorting
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Reservation> reservations = reservationService.getAllNotTreatedReservations(pageable);
        List<Reservation> fetchedReservations = reservations.getContent();
        int totalPages = reservations.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        int currentPage = reservations.getNumber();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("reservations", fetchedReservations);
        return "admin/reservations/treated";
    }

    @GetMapping("/admin/reservations/show/{id}")
    public String show(Model model, @PathVariable int id) {
        Reservation reservation = reservationService.getReservation(id);
        List<ReservationMaterial> reservationMaterials = reservationMaterialService.findByReservationId(id);
        model.addAttribute("reservationMaterials", reservationMaterials);
        model.addAttribute("reservation", reservation);
        double insurance;
        if (reservation.getInsurance()) {
            insurance = generalSettingsService.getGeneralSettings().getInsurance_price();
        } else {
            insurance = 0;
        }
        model.addAttribute("insurance", insurance);
        return "admin/reservations/show";
    }

    @PostMapping("/admin/reservations/treat/{id}")
    public ResponseEntity<String> treat(@PathVariable int id, @RequestBody String status) {
        
        reservationState.addObserver(changeStateInDb);
        reservationState.addObserver(notifyViaEmail);
        reservationState.addObserver(notifyViaSms);
        reservationState.setData(id, status);
        return ResponseEntity.ok("success");
       
    }

}
