package com.example.myapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.myapp.model.request.UpdateUserRequest;
import com.example.myapp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {
        return ResponseEntity.ok(userService.getMyProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(
            @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(userService.updateMyProfile(request));
    }
}
