package com.example.myapp.service;

import com.example.myapp.model.request.UpdateUserRequest;
import com.example.myapp.model.response.UserProfileResponse;

public interface UserService {

    UserProfileResponse getMyProfile();

    UserProfileResponse updateMyProfile(UpdateUserRequest request);
}
