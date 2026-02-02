package com.example.myapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String email;
    private String fullName;
    private String imageUrl;
    private String address;
    private String phone;

    // QUAN TRỌNG
    private String role; // VD: "ROLE_USER", "ROLE_ADMIN"
}
