package com.example.myapp.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.entity.Role;
import com.example.myapp.entity.User;
import com.example.myapp.exception.AppException;
import com.example.myapp.exception.ErrorCode;
import com.example.myapp.model.request.UserCreationRequest;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.model.response.UserResponse;
import com.example.myapp.repository.RoleRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.AdminUserService;
import com.example.myapp.util.PageMapper;
import com.example.myapp.util.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final PageMapper pageMapper;
        private final UserMapper userMapper;

        @Override
        public PageResponse<UserResponse> getUsers(int page, int size) {
                Pageable pageable = PageRequest.of(page, size);
                Page<User> userPage = userRepository.findAll(pageable);
                return pageMapper.toPageResponse(userPage, userMapper::toResponse);
        }

        @Override
        public UserResponse createUser(UserCreationRequest request) {
                if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                        throw new AppException(ErrorCode.USER_EMAIL_EXISTED);
                }

                User user = User.builder()
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .fullName(request.getFullName())
                                .phone(request.getPhone())
                                .address(request.getAddress())
                                .build();

                Set<Role> roles = request.getRoleNames().stream()
                                .map(roleName -> roleRepository.findByName(roleName)
                                                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)))
                                .collect(Collectors.toSet());
                user.setRoles(roles);

                user = userRepository.save(user);
                return userMapper.toResponse(user);
        }

        @Override
        public UserResponse updateUserRoles(Long userId, Set<String> roleNames) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

                Set<Role> roles = roleNames.stream()
                                .map(roleName -> roleRepository.findByName(roleName)
                                                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)))
                                .collect(Collectors.toSet());
                user.setRoles(roles);

                user = userRepository.save(user);
                return userMapper.toResponse(user);
        }

        @Override
        public void deleteUser(Long userId) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                userRepository.delete(user);
        }

        public PageResponse<UserResponse> searchUsers(String keyword, int page, int size) {
                Pageable pageable = PageRequest.of(page, size);
                Page<User> userPage = userRepository.findByFullNameContaining(keyword, pageable);
                return pageMapper.toPageResponse(userPage, user -> userMapper.toResponse(user));
        }

}
