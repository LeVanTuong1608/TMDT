package com.example.myapp.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PermissionResponse {
    private Long id;
    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;
}
