package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.CategoryDto;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryRepository.findAll(sort);
    }

    public List<Category> getAllActiveCategories() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryRepository.findAllByStatus(true, sort);
    }

    public void saveCategory(CategoryDto categorydto) {
        MultipartFile image = categorydto.getImage();
        String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        String uniqueFileName = "img_" + UUID.randomUUID().toString().substring(0, 8) + "." + fileExtension;
        try {
            String uploadDir = "src/main/resources/static/upload/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath))
                Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(image.getInputStream(), filePath);
        } catch (IOException e) {

        }

        Category category = new Category(categorydto.getName(), categorydto.getDescription(), uniqueFileName,
                categorydto.getStatus());
        categoryRepository.save(category);
    }

    public CategoryDto getCategory(int id) {
        Category category = null;
        try {
            category = categoryRepository.findById(id).get();
        } catch (Exception e) {
            return null;
        }

        return new CategoryDto(category.getId(), category.getName(), category.getDescription(), category.getStatus());
    }

    public Category getCategoryInstance(int id) {
        Category category = null;
        try {
            category = categoryRepository.findById(id).get();
            return category;
        } catch (Exception e) {
            return null;
        }

    }

    public void updateCategory(int id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).get();
        MultipartFile image = categoryDto.getImage();
        String uniqueFileName;
        if (!image.isEmpty()) {
            String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            uniqueFileName = "img_" + UUID.randomUUID().toString().substring(0, 8) + "." + fileExtension;
            try {
                String uploadDir = "src/main/resources/static/upload/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath))
                    Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(uniqueFileName);
                Files.copy(image.getInputStream(), filePath);
                Files.delete(uploadPath.resolve(category.getImage()));
            } catch (IOException e) {

            }

        } else {
            uniqueFileName = category.getImage();
        }

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setImage(uniqueFileName);
        category.setStatus(categoryDto.getStatus());
        categoryRepository.save(category);
    }

    public void deleteCategory(int id) {
        String img = categoryRepository.findById(id).get().getImage();
        String uploadDir = "src/main/resources/static/upload/";
        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(img);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoryRepository.deleteById(id);
    }

}
