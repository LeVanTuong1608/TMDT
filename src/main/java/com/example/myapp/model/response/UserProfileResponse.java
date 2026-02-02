package com.example.myapp.model.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String email;
    String fullName;
    String address;
    String phone;
    String imageUrl;
    Set<String> roles;
}
