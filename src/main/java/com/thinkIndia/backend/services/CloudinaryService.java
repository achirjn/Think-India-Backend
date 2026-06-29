package com.thinkIndia.backend.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "resource_type", "auto"
        ));
        return (String) uploadResult.get("secure_url");
    }

    public void deleteFile(String key) {
        try {
            String publicId = extractPublicId(key);
            if (publicId != null && !publicId.isEmpty()) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch (Exception e) {
            System.err.println("Failed to delete file from Cloudinary: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String extractPublicId(String key) {
        if (key == null) return null;
        
        // Key might look like: "mycloud/image/upload/v1719600000/my_folder/my_image.png"
        // or just "mycloud/image/upload/my_image.png"
        int uploadIndex = key.indexOf("/upload/");
        if (uploadIndex == -1) {
            // Fallback: if "/upload/" is not present, strip extension if present
            int dotIndex = key.lastIndexOf('.');
            return (dotIndex == -1) ? key : key.substring(0, dotIndex);
        }
        
        // Get everything after "/upload/"
        String pathAfterUpload = key.substring(uploadIndex + "/upload/".length());
        
        // Remove version component (e.g. "v123456789/") if present
        if (pathAfterUpload.startsWith("v")) {
            int firstSlashIndex = pathAfterUpload.indexOf('/');
            if (firstSlashIndex != -1) {
                String versionPart = pathAfterUpload.substring(1, firstSlashIndex);
                if (versionPart.matches("\\d+")) { // check if it's all digits
                    pathAfterUpload = pathAfterUpload.substring(firstSlashIndex + 1);
                }
            }
        }
        
        // Remove file extension (e.g. ".png", ".jpg")
        int dotIndex = pathAfterUpload.lastIndexOf('.');
        if (dotIndex != -1) {
            pathAfterUpload = pathAfterUpload.substring(0, dotIndex);
        }
        
        return pathAfterUpload;
    }
}
