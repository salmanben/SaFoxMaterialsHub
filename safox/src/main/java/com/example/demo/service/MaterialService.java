package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.MaterialDto;
import com.example.demo.model.Material;
import com.example.demo.repository.MaterialRepository;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<Material> getAllMaterials() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return materialRepository.findAll(sort);
    }

    public void saveMaterial(MaterialDto materialdto) {
        MultipartFile image = materialdto.getImage();
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

        Material material = new Material(materialdto.getName(), materialdto.getDescription(), uniqueFileName,
                materialdto.getPrice_by_day(), materialdto.getShipped_price(), materialdto.getStock(),
                materialdto.getCategory());
        materialRepository.save(material);

    }

    public MaterialDto getMaterial(int id) {
        Material material = materialRepository.findById(id).get();
        MaterialDto materialDto = new MaterialDto(material.getId(), material.getName(), material.getDescription(),
                material.getPrice_by_day(), material.getShipped_price(), material.getStock(), material.getCategory());
        return materialDto;
    }

    public Material getMaterialInstance(int id) {
        return materialRepository.findById(id).get();
    }

    public void updateMaterial(MaterialDto materialDto) {
        Material material = materialRepository.findById(materialDto.getId()).get();

        MultipartFile image = materialDto.getImage();

        String uniqueFileName;
        if (!image.isEmpty()) {
            String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            uniqueFileName = "img_" + UUID.randomUUID().toString().substring(0, 8) + "." + fileExtension;
            System.out.println("unique file name: " + uniqueFileName);
            try {
                String uploadDir = "src/main/resources/static/upload/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath))
                    Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(uniqueFileName);
                Files.copy(image.getInputStream(), filePath);
                Files.delete(uploadPath.resolve(material.getImage()));
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } else {
            uniqueFileName = material.getImage();
        }
        material.setName(materialDto.getName());
        material.setDescription(materialDto.getDescription());
        material.setPrice_by_day(materialDto.getPrice_by_day());
        material.setShipped_price(materialDto.getShipped_price());
        material.setStock(materialDto.getStock());
        material.setCategory(materialDto.getCategory());
        material.setImage(uniqueFileName);
        materialRepository.save(material);

    }

    public void deleteMaterial(int id) {

        String img = materialRepository.findById(id).get().getImage();
        String uploadDir = "src/main/resources/static/upload/";
        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(img);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        materialRepository.deleteById(id);
    }

    // update material stock
    public void saveMaterial(Material material) {
        materialRepository.save(material);
    }

    // find all materials by name containing or description containing
    public List<Material> search(String keyword) {
        return materialRepository.search(keyword);
    }

    public Page<Material> findAllWithPag(Pageable pageable) {
        return materialRepository.findAll(pageable);
    }

    
}
