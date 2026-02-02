package com.example.myapp.exception;

public class UserAlreadyExistsException extends AppException {
    public UserAlreadyExistsException() {
        super(ErrorCode.USER_EMAIL_EXISTED);
    }
}
