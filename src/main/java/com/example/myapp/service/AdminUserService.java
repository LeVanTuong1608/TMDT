package com.example.myapp.service;

import com.example.myapp.model.request.UserCreationRequest;
import com.example.myapp.model.request.UpdateUserRequest;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.model.response.UserResponse;
import java.util.Set;

public interface AdminUserService {
    PageResponse<UserResponse> getUsers(int page, int size);

    UserResponse createUser(UserCreationRequest request);

    UserResponse updateUserRoles(Long userId, Set<String> roleNames);

    void deleteUser(Long userId);

    PageResponse<UserResponse> searchUsers(String keyword, int page, int size);

}
