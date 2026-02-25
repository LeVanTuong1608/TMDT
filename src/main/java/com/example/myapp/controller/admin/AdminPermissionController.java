// package com.example.myapp.controller.admin;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.myapp.model.request.PermissionRequest;
// import com.example.myapp.model.response.PageResponse;
// import com.example.myapp.model.response.PermissionResponse;
// import com.example.myapp.service.AdminPermissionService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.parameters.RequestBody;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/api/admin/permissions")
// @PreAuthorize("hasRole('ADMIN')")
// @Tag(name = "Admin - Permissions")
// @Validated
// public class AdminPermissionController {
//     private final AdminPermissionService adminPermissionService;

//     public AdminPermissionController(AdminPermissionService adminPermissionService) {
//         this.adminPermissionService = adminPermissionService;
//     }

//     @PostMapping
//     @Operation(summary = "Create new permission")
//     public ResponseEntity<PermissionResponse> createPermission(
//             @Valid @RequestBody PermissionRequest request) {

//         PermissionResponse response = adminPermissionService.createPermission(request);
//         return ResponseEntity.status(HttpStatus.CREATED).body(response);
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update permission")
//     public ResponseEntity<PermissionResponse> updatePermission(
//             @PathVariable Long id,
//             @Valid @RequestBody PermissionRequest request) {

//         PermissionResponse response = adminPermissionService.updatePermission(id, request);
//         return ResponseEntity.ok(response);
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete permission")
//     public ResponseEntity<Void> delete(@PathVariable Long id) {
//         adminPermissionService.deletePermission(id);
//         return ResponseEntity.noContent().build();
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get detail of permission")
//     public ResponseEntity<PermissionResponse> getDetail(@PathVariable Long id) {
//         PermissionResponse response = adminPermissionService.getDetail(id);
//         return ResponseEntity.ok(response);
//     }

//     @GetMapping("/search")
//     public ResponseEntity<PageResponse<PermissionResponse>> search(
//             @RequestParam(required = false, defaultValue = "") String keyword,
//             @RequestParam(defaultValue = "0") int page,
//             @RequestParam(defaultValue = "10") int size) {
//         PageResponse<PermissionResponse> response = adminPermissionService.searchPermissions(keyword, page, size);
//         return ResponseEntity.ok(response);
//     }

// }
