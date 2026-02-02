package com.example.myapp.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    @Column(nullable = false, length = 50)
    String name; // ADMIN, USER, STAFF

    String description;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Permission> permissions;
}
