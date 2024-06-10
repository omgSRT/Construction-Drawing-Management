package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.PermissionRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Permission;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/permission")
@Slf4j
public class PermissionController {
    private final PermissionService permissionService;

    @Operation(summary = "Create Permission", description = "Create A Brand New Permission")
    @PostMapping(path = "/create")
    public ApiResponse<Permission> createPermission(@RequestBody @Valid PermissionRequest request){
        return ApiResponse.<Permission>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(permissionService.createPermission(request))
                .build();
    }

    @Operation(summary = "Get Permissions", description = "Get All Permissions")
    @GetMapping(path = "/getall")
    public ApiResponse<List<Permission>> getPermissions(int page, int perPage) {
        return ApiResponse.<List<Permission>>builder()
                .entity(permissionService.getAllPermissions(page, perPage))
                .build();
    }

    @Operation(summary = "Get Permission", description = "Get Permission by ID")
    @GetMapping(path = "/get/{id}")
    public ApiResponse<Permission> getPermissionById(@PathVariable UUID id){
        return ApiResponse.<Permission>builder()
                .message(SuccessReturnMessage.SEARCH_SUCCESS.getMessage())
                .entity(permissionService.findPermissionById(id))
                .build();
    }

    @Operation(summary = "Update Permission", description = "Update Permission by ID")
    @PutMapping(path = "/update/{id}")
    public ApiResponse<Permission> updatePermissionById(@PathVariable UUID id, @RequestBody @Valid PermissionRequest request){
        return ApiResponse.<Permission>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(permissionService.updatePermissionById(id, request))
                .build();
    }

    @Operation(summary = "Delete Permission", description = "Delete Permission by ID")
    @DeleteMapping(path = "/delete/{id}")
    public ApiResponse<Permission> deletePermissionById(@PathVariable UUID id){
        Permission permission = permissionService.findPermissionById(id);
        permissionService.deletePermissionById(id);
        return ApiResponse.<Permission>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(permission)
                .build();
    }

}
