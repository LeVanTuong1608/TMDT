package com.example.myapp.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    String accessToken;
    String refreshToken;
}
