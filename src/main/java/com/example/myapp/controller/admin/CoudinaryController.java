package com.example.myapp.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.model.response.UploadResponse;
import com.example.myapp.service.CloudinaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
public class CoudinaryController {
    private final CloudinaryService cloudinaryService;

    @PostMapping("/images")
    public ResponseEntity<UploadResponse> uploadImage(
            @RequestParam("file") MultipartFile file) {

        String imageUrl = cloudinaryService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UploadResponse(imageUrl));
    }
}
