package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

import com.example.demo.model.Category;
import com.example.demo.model.Reservation;
import com.example.demo.model.ReservationMaterial;
import com.example.demo.model.User;
import com.example.demo.DTO.ContactDto;
import com.example.demo.DTO.GeneralSettingsDto;
import com.example.demo.DTO.UserDto;
import com.example.demo.service.CategoryService;
import com.example.demo.service.GeneralSettingsService;
import com.example.demo.service.ReservationMaterialService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ClientProfileController {

    private final GeneralSettingsService generalSettingsService;
    private final CategoryService categoryService;
    private final ReservationService reservationService;
    private final UserService userService;
    private final ReservationMaterialService reservationMaterialService;

    public ClientProfileController(GeneralSettingsService generalSettingsService, CategoryService categoryService,
            ReservationService reservationService, UserService userService,
            ReservationMaterialService reservationMaterialService) {
        this.generalSettingsService = generalSettingsService;
        this.categoryService = categoryService;
        this.reservationService = reservationService;
        this.userService = userService;
        this.reservationMaterialService = reservationMaterialService;
    }

    @GetMapping("/client/profile")
    public String index(Model model, HttpServletRequest request, @ModelAttribute("contactDto") ContactDto contactDto) {

        GeneralSettingsDto generalSettingsDto = generalSettingsService.getGeneralSettings();
        List<Category> categories = categoryService.getAllActiveCategories();
        HttpSession session = request.getSession();
        int count = 0;
        if (session.getAttribute("cart") != null) {
            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
            count = cart.size();
        }

        // all client res
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User client = userService.getUserInstance(email);
        List<Reservation> reservations = reservationService.getReservationsByClient(client);
        UserDto clientDto = userService.getUser(client.getId());
        model.addAttribute("generalSettings", generalSettingsDto);
        model.addAttribute("categories", categories);
        model.addAttribute("count", count);
        model.addAttribute("reservations", reservations);
        model.addAttribute("clientDto", clientDto);

        return "client/profile/index";
    }

    @GetMapping("/client/reservations/show/{id}")
    public String show(Model model, @PathVariable int id, @ModelAttribute("contactDto") ContactDto contactDto,
            HttpServletRequest request) {
        Reservation reservation = reservationService.getReservation(id);
        if (reservation == null || reservation.getClient().getId() != userService
                .getUserInstance(SecurityContextHolder.getContext().getAuthentication().getName()).getId()) {
            return "redirect:/client/profile";
        }
        List<ReservationMaterial> reservationMaterials = reservationMaterialService.findByReservationId(id);
        model.addAttribute("reservationMaterials", reservationMaterials);
        model.addAttribute("reservation", reservation);
        double insurance;
        if (reservation.getInsurance()) {
            insurance = generalSettingsService.getGeneralSettings().getInsurance_price();
        } else {
            insurance = 0;
        }
        GeneralSettingsDto generalSettingsDto = generalSettingsService.getGeneralSettings();
        List<Category> categories = categoryService.getAllActiveCategories();
        HttpSession session = request.getSession();
        int count = 0;
        if (session.getAttribute("cart") != null) {
            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
            count = cart.size();
        }
        model.addAttribute("generalSettings", generalSettingsDto);
        model.addAttribute("categories", categories);
        model.addAttribute("count", count);
        model.addAttribute("insurance", insurance);
        return "client/profile/show";
    }

    @PostMapping("/client/profile/update/{id}")
    public String update(@Valid @ModelAttribute("clientDto") UserDto clientDto, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model, HttpServletRequest request,
            @ModelAttribute("contactDto") ContactDto contactDto,
            @PathVariable("id") int id) {
        
        if (bindingResult.hasErrors()) {
            GeneralSettingsDto generalSettingsDto = generalSettingsService.getGeneralSettings();
            List<Category> categories = categoryService.getAllActiveCategories();
            HttpSession session = request.getSession();
            
            int count = 0;
            if (session.getAttribute("cart") != null) {
                Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
                count = cart.size();
            }
            model.addAttribute("generalSettings", generalSettingsDto);
            model.addAttribute("categories", categories);
            model.addAttribute("count", count);
            return "client/profile/index";
        }
        UserDto user = userService.findByEmail(clientDto.getEmail());
        UserDto user_ = userService.findByPhone(clientDto.getPhone());
        boolean isEmailExist = false;
        boolean isPhoneExist = false;
        if(user != null && user.getId() != id){
            isEmailExist = true;
        }

        if(user_ != null && user_.getId() != id){
            isPhoneExist = true;
        }

        if(isEmailExist || isPhoneExist){
            if(isEmailExist){
                bindingResult.rejectValue("email", "error.user", "Email is already registered");
            }
            if(isPhoneExist){
                bindingResult.rejectValue("phone", "error.user", "Phone is already registered");
            }
            GeneralSettingsDto generalSettingsDto = generalSettingsService.getGeneralSettings();
            List<Category> categories = categoryService.getAllActiveCategories();
            HttpSession session = request.getSession();
            int count = 0;
            if (session.getAttribute("cart") != null) {
                Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
                count = cart.size();
            }
    
            model.addAttribute("generalSettings", generalSettingsDto);
            model.addAttribute("categories", categories);
            model.addAttribute("count", count);
            return  "client/profile/index";
        
        }

        userService.update(id, clientDto);
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
        return "redirect:/client/profile";
    }

}
