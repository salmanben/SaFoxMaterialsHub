package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.util.StringUtils;
import com.example.demo.service.CategoryService;
import com.example.demo.util.ValidImage;
import com.example.demo.DTO.CategoryDto;
import com.example.demo.model.Category;
import java.util.List;
import jakarta.validation.Valid;

@Controller
public class CategoryController {

    private CategoryService categoryService;
    private ValidImage validImage;

    public CategoryController(CategoryService categoryService, ValidImage validImage) {
        this.categoryService = categoryService;
        this.validImage = validImage;
    }

    @GetMapping("/admin/categories")
    public String index(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/categories/index";
    }

    @GetMapping("/admin/categories/create")
    public String create(@ModelAttribute("categoryDto") CategoryDto categoryDto) {
        return "admin/categories/create";
    }

    @PostMapping("/admin/categories/store")
    public String store(@Valid @ModelAttribute("categoryDto") CategoryDto categoryDto, BindingResult result) {
        MultipartFile image = categoryDto.getImage();
        if (result.hasErrors())
            return "admin/categories/create";
        if (image.isEmpty()) {
            result.rejectValue("image", null, "Please select an image file");
            return "admin/categories/create";
        }
        String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
        if (!validImage.isImage(originalFilename)) {
            result.rejectValue("image", null,
                    "Please upload a valid image file (png, jpeg, jpg, webp)");
            return "admin/categories/create";
        }
        if (image.getSize() > 2000000) {
            result.rejectValue("image", null, "Image size must be less than 2MB");
            return "admin/categories/create";
        }
        categoryService.saveCategory(categoryDto);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        CategoryDto categoryDto = categoryService.getCategory(id);
        model.addAttribute("categoryDto", categoryDto);
        return "admin/categories/edit";
    }

    @PostMapping("/admin/categories/update/{id}")
    public String update(@PathVariable int id,
            @Valid @ModelAttribute("categoryDto") CategoryDto categoryDto, BindingResult result) {
        if (result.hasErrors())
            return "admin/categories/edit";
        MultipartFile image = categoryDto.getImage();
        if (!image.isEmpty()) {
            String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
            if (!validImage.isImage(originalFilename)) {
                result.rejectValue("image", null,
                        "Please upload a valid image file (png, jpeg, jpg, webp)");
                return "admin/categories/edit";
            }
            if (image.getSize() > 2000000) {
                result.rejectValue("image", null, "Image size must be less than 2MB");
                return "admin/categories/edit";
            }
        }
        categoryService.updateCategory(id, categoryDto);
        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/categories/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Category category = categoryService.getCategoryInstance(id);
        if(category.getMaterials().size() > 0)
        {
            redirectAttributes.addFlashAttribute("error", "Category is not empty, cannot be deleted");
            return "redirect:/admin/categories";

        }
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

}
