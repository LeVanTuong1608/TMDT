package com.example.myapp.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @Email
    @Column(nullable = false, unique = true, length = 100)
    String email;

    @Column(nullable = false)
    String password;

    @Column(name = "full_name", length = 100)
    String fullName;

    @Column(name = "image_url")
    String imageUrl;

    String address;

    String phone;

    LocalDate dob;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;

}
