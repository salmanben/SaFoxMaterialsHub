package com.example.demo.controller.auth;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.DTO.ContactDto;
import com.example.demo.DTO.GeneralSettingsDto;
import com.example.demo.DTO.LoginDto;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.service.GeneralSettingsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final GeneralSettingsService generalSettingsService;
    private final CategoryService categoryService;

    public LoginController(GeneralSettingsService generalSettingsService, CategoryService categoryService) {
        this.generalSettingsService = generalSettingsService;
        this.categoryService = categoryService;
    }

    @GetMapping("/login")
    public String login_client(@ModelAttribute("loginDto") LoginDto loginDto, @ModelAttribute("contactDto") ContactDto contactDto,  HttpServletRequest request, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/dashboard";
            }
            return "redirect:/";
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
        return "auth/login";
    }

}
