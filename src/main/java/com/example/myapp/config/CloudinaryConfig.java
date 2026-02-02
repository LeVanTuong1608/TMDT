package com.example.myapp.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig{

    @Bean
    public Cloudinary cloudinary() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "domnsybxh");
        config.put("api_key", "986561924766832");
        config.put("api_secret", "GlOB--NKE0BCWPxM6kA6olU4Kag");
        config.put("secure", true);
        return new Cloudinary(config);
    }
}

