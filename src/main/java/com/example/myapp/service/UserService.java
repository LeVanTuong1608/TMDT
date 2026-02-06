package com.example.myapp.service;

import com.example.myapp.model.request.UpdateUserRequest;
import com.example.myapp.model.response.UserResponse;

public interface UserService {

    UserResponse getMyProfile();

    UserResponse updateMyProfile(UpdateUserRequest request);
}
