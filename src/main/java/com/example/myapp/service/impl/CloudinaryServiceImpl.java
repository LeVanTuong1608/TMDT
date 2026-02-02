package com.example.myapp.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.service.CloudinaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file) {

        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of("folder", "myapp"));

            return result.get("secure_url").toString();

        } catch (Exception e) {
            throw new AppException(ErrorCode.UPLOAD_IMAGE_FAILED);
        }
    }
}
