package com.example.myapp.service;

import com.example.myapp.model.request.*;
import com.example.myapp.model.response.*;

public interface AdminUserService {
    PageResponse<UserResponse> getUsers(int page, int size);

    // UserResponse createUser(RegisterRequest request);

    // UserResponse updateUsers(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    PageResponse<UserResponse> searchUsers(String keyword, int page, int size);

}
