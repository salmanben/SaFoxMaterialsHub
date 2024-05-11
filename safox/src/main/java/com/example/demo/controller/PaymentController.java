package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.CartDto;
import com.example.demo.DTO.PaymentRequestDto;
import com.example.demo.model.Material;
import com.example.demo.model.Reservation;
import com.example.demo.model.ReservationMaterial;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.service.MaterialService;
import com.example.demo.service.ReservationMaterialService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;
import com.example.demo.strategy_payment.Context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {

    private final Context paymenContext;
    private final MaterialService materialService;
    private final TransactionService transactionService;
    private final UserService userService;
    private final ReservationService reservationService;
    @Autowired
    ReservationMaterialService reservationMaterialService;

    @Autowired
    public PaymentController(Context paymenContext, MaterialService materialService,
            TransactionService transactionService, UserService userService, ReservationService reservationService) {
        this.paymenContext = paymenContext;
        this.materialService = materialService;
        this.transactionService = transactionService;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @PostMapping("/client/payment")
    public String payer(@RequestParam String method,
            @RequestBody @ModelAttribute("paymentRequestDto") PaymentRequestDto paymentRequestDto,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        

        HttpSession session = request.getSession();
        Map<String, Object> responseStock = checkStock(session);
        if (responseStock.containsKey("error")) {
            redirectAttributes.addFlashAttribute("error", responseStock.get("error"));
            redirectAttributes.addFlashAttribute("outOfStock", responseStock.get("outOfStock"));
            return "redirect:/cart";
        }
        Map<String, Object> response = (Map<String, Object>) session.getAttribute("invoice");
        double amount = (double) response.get("total");
        paymentRequestDto.setAmount(amount);
        paymentRequestDto.setCurrency("USD");
        String transaction_id = paymenContext.executePayment(method, paymentRequestDto);
        if (transaction_id == null) {
            redirectAttributes.addFlashAttribute("error", "Payment failed");
            return "redirect:/cart";
        }
        storeTransaction(transaction_id, amount, method);
        processReservation(session);

        redirectAttributes.addFlashAttribute("success", "Payment successful");

        return "redirect:/";
    }

    public void storeTransaction(String transaction_id, double amount, String method) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User client = userService.findByEmailInstance(email);

        Transaction transaction = new Transaction(transaction_id, amount, method, client);
        transactionService.saveTransaction(transaction);
    }

    public void processReservation(HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        Reservation reservation = new Reservation();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();
        Map<String, Object> response = (Map<String, Object>) session.getAttribute("invoice");

        User client = userService.findByEmailInstance(email);
        reservation.setClient(client);
        // reservation.setMaterials(materialService.getMaterials(cart));
        reservation.setTotal((double) response.get("total"));
        reservation.setSubtotal((double) response.get("subtotal"));
        reservation.setDiscount((double) response.get("discount"));
        reservation.setShipping_cost((double) response.get("shipping"));
        reservation.setStatus("pending");
        reservation.setShipping_address((String) session.getAttribute("shipping_address"));
        boolean insurance = (int) session.getAttribute("insurance") == 1 ? true : false;
        reservation.setInsurance(insurance);
        String startDateString = (String) session.getAttribute("start");
        String endDateString = (String) session.getAttribute("end");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            java.util.Date startDateUtil = sdf.parse(startDateString);
            java.sql.Date startDate = new java.sql.Date(startDateUtil.getTime());

            java.util.Date endDateUtil = sdf.parse(endDateString);
            java.sql.Date endDate = new java.sql.Date(endDateUtil.getTime());

            reservation.setStart_date(startDate);
            reservation.setEnd_date(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        reservation.setCreated_at(new Timestamp(System.currentTimeMillis()));

        Reservation savedReservation = reservationService.saveReservation(reservation);
        for (Integer key : cart.keySet()) {

            Material material = materialService.getMaterialInstance(key);
            ReservationMaterial reservationMaterial = new ReservationMaterial(savedReservation, material,
                    cart.get(key));
            reservationMaterialService.save(reservationMaterial);
        }

        session.removeAttribute("invoice");
        session.removeAttribute("cart");

    }


    public Map<String, Object> checkStock(HttpSession session)
    {
        Map<String, Object> response = new HashMap<>();
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        List<Material> outOfStock = new ArrayList<>();
        for (Integer key : cart.keySet()) {
            Material material = materialService.getMaterialInstance(key);

            String startDateString = (String) session.getAttribute("start");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

          try {
            java.util.Date startDateUtil = sdf.parse(startDateString);
            java.sql.Date startDate = new java.sql.Date(startDateUtil.getTime());

            List<Reservation> activeReservationsForDate = 
            reservationService.findActiveReservationsOfADate(startDate);
            for (Reservation reservation : activeReservationsForDate) {
                int qty = 0;
                List<ReservationMaterial> rm = reservationMaterialService.findByReservationId(reservation.getId());
                for (ReservationMaterial reservationMaterial : rm) {
                    if (reservationMaterial.getMaterial().getId() == material.getId()) {
                        qty += reservationMaterial.getQuantity();
                    }
                }
                if (material.getStock()  < qty + cart.get(key)) {
                    outOfStock.add(material);
                    return response;
                }
            }
          } catch (Exception e) {
            // TODO: handle exception
          }

            


        }
        if (outOfStock.size() > 0) {
            response.put("error", "Some materials are out of stock");
            response.put("outOfStock", outOfStock);
            return response;
        }
        return response;
    }

}
