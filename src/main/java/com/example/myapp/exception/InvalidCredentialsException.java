package com.example.myapp.exception;

public class InvalidCredentialsException extends AppException {
    public InvalidCredentialsException() {
        super(ErrorCode.USER_EMAIL_EXISTED);
    }
}
