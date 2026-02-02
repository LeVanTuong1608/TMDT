package com.example.myapp.model.response;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String email;
    private Set<String> roles;
}
