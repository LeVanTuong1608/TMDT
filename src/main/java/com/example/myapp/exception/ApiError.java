package com.example.myapp.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiError {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
