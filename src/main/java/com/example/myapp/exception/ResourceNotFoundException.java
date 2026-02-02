package com.example.myapp.exception;

public class ResourceNotFoundException extends AppException {

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
