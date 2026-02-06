package com.example.myapp.service.impl;

import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.myapp.entity.User;
import com.example.myapp.exception.InvalidPasswordException;
import com.example.myapp.exception.UserNotFoundException;
import com.example.myapp.model.request.UpdateUserRequest;
import com.example.myapp.model.response.UserResponse;
import com.example.myapp.repository.UserfrofileRepository;
import com.example.myapp.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserfrofileRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getMyProfile() {
        User user = getCurrentUser();
        return mapToResponse(user);
    }

    @Override
    public UserResponse updateMyProfile(UpdateUserRequest request) {

        User user = getCurrentUser();

        // update info
        user.setFullName(request.getFullName());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        // user.setDob(request.getDob());
        user.setImageUrl(request.getImageUrl());

        // đổi mật khẩu (nếu có)
        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {

            if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new InvalidPasswordException();
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        userRepository.save(user);
        return mapToResponse(user);
    }

    /* ================= HELPER ================= */

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .phone(user.getPhone())
                // .dob(user.getDob())
                .imageUrl(user.getImageUrl())
                .roles(user.getRoles()
                        .stream()
                        .map(r -> r.getName())
                        .collect(Collectors.toSet()))
                .build();
    }
}
