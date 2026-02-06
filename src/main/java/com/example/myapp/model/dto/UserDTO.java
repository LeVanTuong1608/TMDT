package com.example.myapp.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;
    private String email;
    private String fullName;
    private String imageUrl;
    private String address;
    private String phone;
    private LocalDate dob;
    private Set<String> roles;
}
