package com.example.myapp.exception;

public class InvalidTokenException extends AppException {
    public InvalidTokenException() {
        super(ErrorCode.PASSWORD_RESET_TOKEN_INVALID);
    }
}
