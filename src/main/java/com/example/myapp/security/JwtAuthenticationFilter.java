// // package com.example.myapp.security;

// // import java.util.List;

// // import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// // import org.springframework.security.core.authority.SimpleGrantedAuthority;
// // import org.springframework.security.core.context.SecurityContextHolder;
// // import org.springframework.stereotype.Component;
// // import org.springframework.web.filter.OncePerRequestFilter;

// // import com.example.myapp.entity.User;
// // import com.example.myapp.exception.UserNotFoundException;
// // import com.example.myapp.repository.*;
// // import java.io.IOException;
// // import jakarta.servlet.FilterChain;
// // import jakarta.servlet.ServletException;
// // import jakarta.servlet.http.HttpServletRequest;
// // import jakarta.servlet.http.HttpServletResponse;
// // import lombok.*;

// // @Component
// // @RequiredArgsConstructor
// // public class JwtAuthenticationFilter extends OncePerRequestFilter {
// //         private final JwtService jwtService;
// //         private final UserRepository userRepository;

// //         @Override
// //         protected void doFilterInternal(HttpServletRequest request,
// //                         HttpServletResponse response,
// //                         FilterChain filterChain)
// //                         throws ServletException, IOException, java.io.IOException {

// //                 String authHeader = request.getHeader("Authorization");

// //                 if (authHeader == null || !authHeader.startsWith("Bearer ")) {
// //                         filterChain.doFilter(request, response);
// //                         return;
// //                 }

// //                 String token = authHeader.substring(7);
// //                 String email = jwtService.extractEmail(token);

// //                 User user = userRepository.findByEmail(email)
// //                                 .orElseThrow(UserNotFoundException::new);

// //                 List<SimpleGrantedAuthority> authorities = jwtService.extractRoles(token)
// //                                 .stream()
// //                                 .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
// //                                 .toList();

// //                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
// //                                 user.getEmail(),
// //                                 null,
// //                                 authorities);

// //                 SecurityContextHolder.getContext().setAuthentication(authentication);
// //                 filterChain.doFilter(request, response);
// //         }
// // }
// package com.example.myapp.security;

// import java.io.IOException;
// import java.util.List;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.example.myapp.entity.User;
// import com.example.myapp.repository.UserRepository;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;

// @Component
// @RequiredArgsConstructor
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//         private final JwtService jwtService;
//         private final UserRepository userRepository;

//         @Override
//         protected void doFilterInternal(
//                         HttpServletRequest request,
//                         HttpServletResponse response,
//                         FilterChain filterChain)
//                         throws ServletException, IOException {

//                 try {

//                         String authHeader = request.getHeader("Authorization");

//                         if (authHeader != null && authHeader.startsWith("Bearer ")) {

//                                 String token = authHeader.substring(7);

//                                 String email = jwtService.extractEmail(token);

//                                 if (email != null &&
//                                                 SecurityContextHolder.getContext().getAuthentication() == null) {

//                                         User user = userRepository.findByEmail(email).orElse(null);

//                                         if (user != null && jwtService.isTokenValid(token, user)) {

//                                                 List<SimpleGrantedAuthority> authorities = jwtService
//                                                                 .extractRoles(token)
//                                                                 .stream()
//                                                                 .map(role -> new SimpleGrantedAuthority(role))
//                                                                 .toList();

//                                                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                                                                 user.getEmail(),
//                                                                 null,
//                                                                 authorities);

//                                                 SecurityContextHolder.getContext()
//                                                                 .setAuthentication(authentication);
//                                         }
//                                 }
//                         }

//                 } catch (Exception e) {
//                         // ❗ Không được throw exception
//                         // chỉ log lỗi
//                         System.out.println("JWT Error: " + e.getMessage());
//                 }

//                 filterChain.doFilter(request, response);
//         }
// }

package com.example.myapp.security;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.myapp.entity.User;
import com.example.myapp.exception.UserNotFoundException;
import com.example.myapp.repository.*;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
        private final JwtService jwtService;
        private final UserRepository userRepository;

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain)
                        throws ServletException, IOException {

                String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        filterChain.doFilter(request, response);
                        return;
                }

                String token = authHeader.substring(7);
                String email = jwtService.extractEmail(token);

                User user = userRepository.findByEmail(email)
                                .orElseThrow(UserNotFoundException::new);

                List<SimpleGrantedAuthority> authorities = jwtService.extractRoles(token)
                                .stream()
                                // .map(role -> new SimpleGrantedAuthority(role))
                                .map(SimpleGrantedAuthority::new)
                                .toList();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
        }
}
