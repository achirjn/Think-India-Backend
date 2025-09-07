package com.thinkIndia.backend.services;

import java.io.IOException;
import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    private final S3Client s3Client;
    // @Value("${aws_s3_bucket}")
    // private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String key = Instant.now().toEpochMilli() + "_" + file.getOriginalFilename();

        s3Client.putObject(PutObjectRequest.builder()
                        .bucket("thinkindiasvnit-images")
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                software.amazon.awssdk.core.sync.RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        // Public URL (if bucket is public)
        //https://thinkindiasvnit-images.s3.amazonaws.com/
        return "https://thinkindiasvnit-images.s3.amazonaws.com/" + key;
    }

    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket("thinkindiasvnit-images")
                .key(key) // this is the filename/key in S3
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}
