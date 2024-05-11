package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.CartDto;
import com.example.demo.DTO.CategoryDto;
import com.example.demo.DTO.ContactDto;
import com.example.demo.DTO.GeneralSettingsDto;
import com.example.demo.model.Category;
import com.example.demo.model.Material;
import com.example.demo.service.CategoryService;
import com.example.demo.service.GeneralSettingsService;
import com.example.demo.service.MaterialService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeMaterialController {

    private final GeneralSettingsService generalSettingsService;
    private final CategoryService categoryService;
    private final MaterialService materialService;

    public HomeMaterialController(GeneralSettingsService generalSettingsService, CategoryService categoryService,
            MaterialService materialService) {
        this.generalSettingsService = generalSettingsService;
        this.categoryService = categoryService;
        this.materialService = materialService;
    }


    @GetMapping("/material")
    public String index(@RequestParam("cat") int id, Model model, HttpServletRequest request,
            @ModelAttribute("contactDto") ContactDto contactDto) {

        GeneralSettingsDto generalSettingsDto = generalSettingsService.getGeneralSettings();
        List<Category> categories = categoryService.getAllActiveCategories();
        HttpSession session = request.getSession();
        int count = 0;
        if (session.getAttribute("cart") != null) {
            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
            count = cart.size();
        }

        Category cuerrentCategory = categoryService.getCategoryInstance(id);
        if (cuerrentCategory == null || !cuerrentCategory.getStatus()) {
            return "redirect:/";
        }

        model.addAttribute("generalSettings", generalSettingsDto);
        model.addAttribute("categories", categories);
        model.addAttribute("count", count);
        model.addAttribute("cuerrentCategory", cuerrentCategory);
        
        return "client/product";
    }


    @GetMapping("/material/search")
    public String search(@RequestParam("search") String search, Model model, HttpServletRequest request,
            @ModelAttribute("contactDto") ContactDto contactDto) {

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
        List<Material> materials = materialService.search(search);
        model.addAttribute("materials", materials);        
        return "client/searched_products";
    }

   

}
