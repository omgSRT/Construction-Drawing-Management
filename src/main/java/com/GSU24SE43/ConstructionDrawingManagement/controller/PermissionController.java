package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.PermissionRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Permission;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
