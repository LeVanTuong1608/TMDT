package com.example.myapp.exception;

public class InvalidPasswordException extends AppException {
    public InvalidPasswordException() {
        super(ErrorCode.OLD_PASSWORD_INCORRECT);
    }
}
