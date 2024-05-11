package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

import com.example.demo.DTO.MaterialDto;
import com.example.demo.model.Category;
import com.example.demo.model.Material;
import com.example.demo.model.ReservationMaterial;
import com.example.demo.service.CategoryService;
import com.example.demo.service.MaterialService;
import com.example.demo.service.ReservationMaterialService;
import com.example.demo.util.ValidImage;

import jakarta.validation.Valid;

@Controller
public class MaterialController {

    private final MaterialService materialService;
    private final CategoryService categoryService;
    private final ValidImage validImage;
    private final ReservationMaterialService materialReserveService;

    public MaterialController(MaterialService materialService, CategoryService categoryService, ValidImage validImage,
            ReservationMaterialService materialReserveService) {
        this.materialService = materialService;
        this.categoryService = categoryService;
        this.validImage = validImage;
        this.materialReserveService = materialReserveService;
    }

    @GetMapping("/admin/materials")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());

        Page<Material> materials = materialService.findAllWithPag(pageable);
        int totalPage = materials.getTotalPages();
        System.out.println("Total Page: " + totalPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);

        List<Material> fetchedMaterials = materials.getContent();
        model.addAttribute("materials", fetchedMaterials);
        return "admin/materials/index";
    }

    @GetMapping("/admin/materials/create")
    public String create(@ModelAttribute("materialDto") MaterialDto materialDto, Model model) {
        List<Category> categories = categoryService.getAllActiveCategories();
        model.addAttribute("categories", categories);
        return "admin/materials/create";
    }

    @PostMapping("/admin/materials/store")
    public String store(@Valid @ModelAttribute("materialDto") MaterialDto materialDto, BindingResult result,
            Model model) {
        List<Category> categories = categoryService.getAllActiveCategories();
        model.addAttribute("categories", categories);
        MultipartFile image = materialDto.getImage();
        if (result.hasErrors()) {
            return "admin/materials/create";
        }

        if (image.isEmpty()) {
            result.rejectValue("image", null, "Please select an image file");
            return "admin/materials/create";
        }
        String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
        if (!validImage.isImage(originalFilename)) {
            result.rejectValue("image", null,
                    "Please upload a valid image file (png, jpeg, jpg, webp)");
            return "admin/materials/create";
        }
        if (image.getSize() > 2000000) {
            result.rejectValue("image", null, "Image size must be less than 2MB");
            return "admin/materials/create";
        }
        System.out.println("Hello" + materialDto.getCategory());
        materialService.saveMaterial(materialDto);
        return "redirect:/admin/materials";
    }

    @GetMapping("/admin/materials/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        MaterialDto materialDto = materialService.getMaterial(id);
        List<Category> categories = categoryService.getAllActiveCategories();
        model.addAttribute("materialDto", materialDto);
        model.addAttribute("categories", categories);
        return "admin/materials/edit";
    }

    @PostMapping("/admin/materials/update/{id}")
    public String update(@PathVariable int id, @Valid @ModelAttribute("materialDto") MaterialDto materialDto,
            BindingResult result, Model model) {
        List<Category> categories = categoryService.getAllActiveCategories();
        model.addAttribute("categories", categories);
        MultipartFile image = materialDto.getImage();
        if (result.hasErrors()) {
            return "admin/materials/edit";
        }

        if (!image.isEmpty()) {
            String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
            if (!validImage.isImage(originalFilename)) {
                result.rejectValue("image", null,
                        "Please upload a valid image file (png, jpeg, jpg, webp)");
                return "admin/materials/edit";
            }
            if (image.getSize() > 2000000) {
                result.rejectValue("image", null, "Image size must be less than 2MB");
                return "admin/materials/edit";
            }
        }
        materialService.updateMaterial(materialDto);
        return "redirect:/admin/materials";
    }

    @PostMapping("/admin/materials/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {

        List<ReservationMaterial> reservationMaterial = materialReserveService.findByMaterialId(id);
        if (reservationMaterial.size() > 0) {
            redirectAttributes.addFlashAttribute("error", "Material is reserved by some reservation");
            return "redirect:/admin/materials";
        }
        materialService.deleteMaterial(id);
        return "redirect:/admin/materials";
    }

}
