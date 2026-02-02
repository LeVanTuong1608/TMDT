package com.example.myapp.exception;

public class InvalidRefreshTokenException extends AppException {
    public InvalidRefreshTokenException() {
        super(ErrorCode.TOKEN_INVALID);
    }
}
