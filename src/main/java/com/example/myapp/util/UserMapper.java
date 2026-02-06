package com.example.myapp.util;

import org.springframework.stereotype.Component;
import com.example.myapp.entity.User;
import com.example.myapp.model.response.UserResponse;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .imageUrl(user.getImageUrl())
                .address(user.getAddress())
                .phone(user.getPhone())
                .build();
    }
}
