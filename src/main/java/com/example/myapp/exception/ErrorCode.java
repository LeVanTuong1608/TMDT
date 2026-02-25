package com.example.myapp.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // AUTH
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid credentials"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Invalid token"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Token expired"),
    EMAIL_EXITS(HttpStatus.CONFLICT, "Email already exists"),

    // USER
    USER_EXISTED(HttpStatus.CONFLICT, "User already exists"),
    USER_EMAIL_EXISTED(HttpStatus.CONFLICT, "Email already exists"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),

    // ROLE
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "Role not found"),

    // RESOURCE
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "Book not found"),
    AUTHOR_NOT_FOUND(HttpStatus.NOT_FOUND, "Author not found"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Category not found"),

    // CART
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "Cart not found"),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "Cart item not found"),

    // VALIDATION
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request"),
    OLD_PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "Old password is incorrect"),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "Quantity must be greater than 0"),
    PASSWORD_RESET_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "Invalid or expired password reset token"),

    // SERVER
    UPLOAD_IMAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Upload image failed"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),

    // PERMISSION
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Access denied"),

    // COMMENT
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not found"),

    ACTIVE_CART_NOT_FOUND(HttpStatus.NOT_FOUND, "Active cart not found"),

    // ORRDER
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Order not found"),
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "Order item not found");

    private final HttpStatus status;
    private final String message;
}
