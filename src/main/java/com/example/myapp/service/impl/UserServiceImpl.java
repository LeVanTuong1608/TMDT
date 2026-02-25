// package com.example.myapp.service.impl;

// import java.util.stream.Collectors;

// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Service;

// import com.example.myapp.entity.User;
// import com.example.myapp.exception.AppException;
// import com.example.myapp.exception.ErrorCode;
// import com.example.myapp.exception.InvalidPasswordException;
// import com.example.myapp.exception.UserNotFoundException;
// import com.example.myapp.model.request.UpdateUserRequest;
// import com.example.myapp.model.response.UserResponse;
// import com.example.myapp.repository.UserRepository;
// import com.example.myapp.service.UserService;
// import com.example.myapp.util.UserMapper;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class UserServiceImpl implements UserService {

// private final UserRepository userRepository;
// private final PasswordEncoder passwordEncoder;
// private final UserMapper userMapper;
// // private final RoleMapper roleMapper;

// @Override
// public UserResponse getMyProfile() {
// User user = getCurrentUser();
// return userMapper.toResponse(user);
// }

// @Override
// public UserResponse updateMyProfile(UpdateUserRequest request) {

// User user = getCurrentUser();

// // update info
// user.setFullName(request.getFullName());
// user.setAddress(request.getAddress());
// user.setPhone(request.getPhone());
// // user.setDob(request.getDob());
// user.setImageUrl(request.getImageUrl());

// // đổi mật khẩu (nếu có)
// if (request.getNewPassword() != null && !request.getNewPassword().isBlank())
// {

// if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
// throw new InvalidPasswordException();
// }

// user.setPassword(passwordEncoder.encode(request.getNewPassword()));
// }

// userRepository.save(user);
// return userMapper.toResponse(user);
// }

// /* ================= HELPER ================= */

// private User getCurrentUser() {
// String email = SecurityContextHolder.getContext()
// .getAuthentication()
// .getName();

// User user = userRepository.findByEmail(email)
// .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

// return user;
// }

// }
