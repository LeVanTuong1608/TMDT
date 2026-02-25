package com.example.myapp.service;

import com.example.myapp.model.request.PermissionRequest;
import com.example.myapp.model.response.PageResponse;
import com.example.myapp.model.response.PermissionResponse;

public interface AdminPermissionService {
    PermissionResponse createPermission(PermissionRequest request);

    PermissionResponse updatePermission(Long id, PermissionRequest request);

    void deletePermission(Long id);

    PageResponse<PermissionResponse> getPermissions(int page, int size);

    PageResponse<PermissionResponse> searchPermissions(String keyword, int page, int size);

    PermissionResponse getDetail(Long id);

}
