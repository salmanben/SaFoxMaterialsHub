package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import com.example.demo.DTO.UserDto;
import com.example.demo.service.UserService;
import com.example.demo.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import jakarta.validation.Valid;

@Controller
public class UserController {
    
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    

    @GetMapping("/admin/clients")
    public String clients(Model model, @RequestParam(defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<User> clients = userService.getAllClients(pageable);
        List<User> fetchedClients = clients.getContent();
        int totalPages = clients.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("clients", fetchedClients);
        model.addAttribute("currentPage", clients.getNumber());
        return "admin/users/clients";
    }

    @GetMapping("/admin/admins")
    public String admins(Model model, @RequestParam(defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<User> admins = userService.getAllAdmins(pageable);
        List<User> fetchedAdmins = admins.getContent();
        model.addAttribute("admins", fetchedAdmins);
        model.addAttribute("totalPages", admins.getTotalPages());
        model.addAttribute("currentPage", admins.getNumber());
        return "admin/users/admins";
    }

    @GetMapping("/admin/admins/create")
    public String create(@ModelAttribute("userDto") UserDto userDto){
        return "admin/users/create";
    }

    @PostMapping("/admin/admins/store")
    public String store(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult){
        System.out.println(userDto);
        if(bindingResult.hasErrors()){
            return "admin/users/create";
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
        
            return "admin/users/create";
        }

        userService.store(userDto);
       
        return "redirect:/admin/admins";

    }
   

    @PostMapping("/admin/switchStatus")
    @ResponseBody
    public ResponseEntity<String> switchStatus(@RequestBody int id){

        userService.switchStatus(id);
        return ResponseEntity.ok("success");
    }

}
