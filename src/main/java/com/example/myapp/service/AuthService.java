package com.example.myapp.service;

import org.hibernate.annotations.Comments;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.myapp.model.request.ForgotPasswordRequest;

// import org.springframework.stereotype.Service;

import com.example.myapp.model.request.LoginRequest;
import com.example.myapp.model.request.RefreshTokenRequest;
import com.example.myapp.model.request.RegisterRequest;
import com.example.myapp.model.request.ResetPasswordRequest;
import com.example.myapp.model.response.LoginResponse;
import com.example.myapp.model.response.RefreshTokenResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    void registerUser(RegisterRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

}
