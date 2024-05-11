package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import com.example.demo.model.Category;
import com.example.demo.model.Material;
import com.example.demo.DTO.CartDto;
import com.example.demo.DTO.ContactDto;
import com.example.demo.DTO.GeneralSettingsDto;
import com.example.demo.service.CategoryService;
import com.example.demo.service.GeneralSettingsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private final GeneralSettingsService generalSettingsService;
    private final CategoryService categoryService;
    @Autowired
    private JavaMailSender mailSender;

    public HomeController(GeneralSettingsService generalSettingsService, CategoryService categoryService) {
        this.generalSettingsService = generalSettingsService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request, @ModelAttribute("contactDto") ContactDto contactDto) {

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
        return "client/home";
    }

    @PostMapping("/contact")
    public ResponseEntity<String> contact(@Valid @RequestBody ContactDto contactDto, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.ok("error");
        }
        // send email
        
        SimpleMailMessage msg = new SimpleMailMessage();
        String to = generalSettingsService.getGeneralSettings().getContact_email();
        msg.setTo(to);
        msg.setSubject("Contact");
        msg.setText("Email: " + contactDto.getEmail() + "\nMessage: " + contactDto.getMessage_());
        String from = "SaFOX" + "<" + to + ">";
        msg.setFrom(from);

        mailSender.send(msg);

        return ResponseEntity.ok("success");
    }
}
