package com.example.myapp.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.myapp.model.dto.UserDTO;
import com.example.myapp.model.request.UserCreationRequest;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.service.AdminUserService;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<PageResponse<UserDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminUserService.getUsers(page, size));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreationRequest request) {
        return ResponseEntity.ok(adminUserService.createUser(request));
    }

    @PutMapping("/{userId}/roles") // 
    public ResponseEntity<UserDTO> updateUserRoles(
            @PathVariable Long userId,
            @RequestBody Set<String> roleNames) {
        return ResponseEntity.ok(adminUserService.updateUserRoles(userId, roleNames));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminUserService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
