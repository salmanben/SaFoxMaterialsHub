package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class ValidImage {
    public boolean isImage(String fileName) {
        String[] allowedExtensions = { "png", "jpeg", "jpg", "webp" };
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        for (String ext : allowedExtensions) {
            if (ext.equals(fileExtension)) {
                return true;
            }
        }
        return false;
    }
}
