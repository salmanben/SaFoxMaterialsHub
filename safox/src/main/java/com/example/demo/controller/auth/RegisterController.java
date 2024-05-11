package com.example.demo.controller.auth;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.ContactDto;
import com.example.demo.DTO.GeneralSettingsDto;
import com.example.demo.DTO.UserDto;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.service.GeneralSettingsService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class RegisterController {

    private final UserService userService;
    private final GeneralSettingsService generalSettingsService;
    private final CategoryService categoryService;

    public RegisterController(UserService userService, GeneralSettingsService generalSettingsService,
            CategoryService categoryService) {
        this.userService = userService;
        this.generalSettingsService = generalSettingsService;
        this.categoryService = categoryService;
    }

    @GetMapping("/register")
    public String showRegister(@ModelAttribute("user") UserDto userDto, HttpServletRequest request, Model model, @ModelAttribute("contactDto") ContactDto contactDto) {
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
        return "auth/register";
    }

    @PostMapping("/register/store")
    public String storeRegister(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, HttpServletRequest request, Model model, @ModelAttribute("contactDto") ContactDto contactDto){
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
            return "auth/register";
        }

        boolean isEmailExist = userService.isEmailExist(userDto.getEmail());
        boolean isPhoneExist = userService.isPhoneExist(userDto.getPhone());

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
            return "auth/register";
        
        }
        userService.storeClient(userDto);
        redirectAttributes.addFlashAttribute("success", "You have successfully registered, please login to continue.");
        return "redirect:/login";
    }
}
