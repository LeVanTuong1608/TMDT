package com.example.myapp.service;

import com.example.myapp.model.request.ForgotPasswordRequest;
import com.example.myapp.model.request.LoginRequest;
import com.example.myapp.model.request.RefreshTokenRequest;
import com.example.myapp.model.request.RegisterRequest;
import com.example.myapp.model.request.ResetPasswordRequest;
import com.example.myapp.model.request.UpdateUserRequest;
import com.example.myapp.model.response.LoginResponse;
import com.example.myapp.model.response.RefreshTokenResponse;
import com.example.myapp.model.response.UserResponse;

public interface UserService {
    LoginResponse login(LoginRequest request);

    void registerUser(RegisterRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    void logout(RefreshTokenRequest request);

    void verifyEmail(String token);

    void resendVerificationEmail(String email);

    void changePassword(String oldPassword, String newPassword);

    UserResponse getMyProfile();

    UserResponse updateMyProfile(UpdateUserRequest request);

}
