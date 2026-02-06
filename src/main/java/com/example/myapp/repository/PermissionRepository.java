package com.example.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
