package com.example.myapp.service;

import com.example.myapp.model.dto.UserDTO;
import com.example.myapp.model.request.UserCreationRequest;
import com.example.myapp.model.response.PageResponse;

import java.util.Set;

import org.springframework.stereotype.Service;

public interface AdminUserService {
    PageResponse<UserDTO> getUsers(int page, int size);

    UserDTO createUser(UserCreationRequest request);

    UserDTO updateUserRoles(Long userId, Set<String> roleNames);

    void deleteUser(Long userId);
}
