package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.VersionCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.VersionUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.VersionResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.enums.VersionStatus;
import com.GSU24SE43.ConstructionDrawingManagement.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/version")
@Slf4j
public class VersionController {
    final VersionService versionService;

    @Operation(summary = "Create Version", description = "Create A Brand New Version")
    @PostMapping(path = "/create")
    public ApiResponse<VersionResponse> createVersion(@RequestBody @Valid VersionCreateRequest request){
        return ApiResponse.<VersionResponse>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(versionService.createVersion(request))
                .build();
    }

    @Operation(summary = "Get All Versions", description = "Get All Versions by Status")
    @GetMapping(path = "/getallByStatus")
    public ApiResponse<List<VersionResponse>> getAllVersionsByStatus(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int perPage,
                                                             @RequestParam VersionStatus status){
        return ApiResponse.<List<VersionResponse>>builder()
                .entity(versionService.getAllVersionsByStatus(page, perPage, status))
                .build();
    }

    @Operation(summary = "Get All Versions")
    @GetMapping(path = "/getall")
    public ApiResponse<List<VersionResponse>> getAllVersions(@RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<VersionResponse>>builder()
                .entity(versionService.getAllVersions(page, perPage))
                .build();
    }

    @Operation(summary = "Get All Versions", description = "Get All Versions By Drawing ID and Status")
    @GetMapping(path = "/getallByDrawingIdAndStatus/{drawingId}")
    public ApiResponse<List<VersionResponse>> getAllVersionsByDrawingIdAndStatus(@PathVariable UUID drawingId,
                                                             @RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int perPage,
                                                             @RequestParam VersionStatus status){
        return ApiResponse.<List<VersionResponse>>builder()
                .entity(versionService.getAllVersionsByDrawingIdAndStatus(page, perPage, status, drawingId))
                .build();
    }

    @Operation(summary = "Get All Versions", description = "Get All Versions By Drawing ID")
    @GetMapping(path = "/getallByDrawingId/{drawingId}")
    public ApiResponse<List<VersionResponse>> getAllVersionsByDrawingId(@PathVariable UUID drawingId,
                                                                                 @RequestParam(defaultValue = "1") int page,
                                                                                 @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<VersionResponse>>builder()
                .entity(versionService.getAllVersionsByDrawingId(page, perPage, drawingId))
                .build();
    }

    @Operation(summary = "Delete Version", description = "Delete Version By ID")
    @DeleteMapping(path = "/delete/{id}")
    public ApiResponse<VersionResponse> deleteVersionById(@PathVariable UUID id){
        var versionResponse = versionService.findVersionById(id);
        versionService.deleteVersionById(id);
        return ApiResponse.<VersionResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(versionResponse)
                .build();
    }

    @Operation(summary = "Update Version", description = "Update Version By ID")
    @PutMapping(path = "/update/{id}")
    public ApiResponse<VersionResponse> updateVersionById(@PathVariable UUID id,
                                                          @RequestBody @Valid VersionUpdateRequest request){
        return ApiResponse.<VersionResponse>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(versionService.updateVersionById(id, request))
                .build();
    }

    @Operation(summary = "Get Version", description = "Get Version By ID")
    @GetMapping(path = "/get/{id}")
    public ApiResponse<VersionResponse> getVersionById(@PathVariable UUID id){
        return ApiResponse.<VersionResponse>builder()
                .entity(versionService.findVersionById(id))
                .build();
    }

    @Operation(summary = "Change Version Status", description = "Change Version Status By ID")
    @PutMapping(path = "/change/status/{id}")
    public ApiResponse<VersionResponse> changeVersionStatusById(@PathVariable UUID id){
        return ApiResponse.<VersionResponse>builder()
                .message(SuccessReturnMessage.CHANGE_SUCCESS.getMessage())
                .entity(versionService.changeVersionStatus(id))
                .build();
    }
}
