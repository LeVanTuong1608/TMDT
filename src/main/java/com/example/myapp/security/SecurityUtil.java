package com.example.myapp.security;

import org.springframework.security.core.context.SecurityContextHolder;
import com.example.myapp.entity.User;
import com.example.myapp.exception.UserNotFoundException;

public class SecurityUtil {

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof User user) {
            return user;
        }

        // throw new RuntimeException("User not authenticated");
        throw new UserNotFoundException();
    }

    public static String getCurrentUserLogin() {
        User user = getCurrentUser();
        return user.getEmail();
    }
}
