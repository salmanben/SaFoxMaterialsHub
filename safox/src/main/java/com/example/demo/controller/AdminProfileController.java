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

import com.example.demo.DTO.GeneralSettingsDto;
import com.example.demo.DTO.UserDto;
import com.example.demo.model.Category;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@Controller
public class AdminProfileController {


    private final UserService userService;
    public AdminProfileController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/admin/profile")
    public String index(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        UserDto adminDto = userService.findByEmail(email);
        model.addAttribute("adminDto", adminDto);
        return "admin/profile/index";
    }

    @PostMapping("/admin/profile/update/{id}")
    public String update(@Valid @ModelAttribute("adminDto") UserDto adminDto, BindingResult bindingResult, RedirectAttributes redirectAttributes,
    @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "admin/profile/index";
        }
        
        UserDto user = userService.findByEmail(adminDto.getEmail());
        UserDto user_ = userService.findByPhone(adminDto.getPhone());
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

            return "admin/profile/index";
        
        }
        userService.update(id, adminDto);
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
        return "redirect:/admin/profile";
    }
    
}
