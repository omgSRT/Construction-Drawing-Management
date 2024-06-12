package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.SubfolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.SubfolderUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.SubfolderResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Subfolder;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.SubfolderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subfolder")
@Slf4j
public class SubfolderController {
    final SubfolderService subfolderService;

    @Operation(summary = "Create Subfolder", description = "Create A Brand New Subfolder")
    @PostMapping("/create")
    public ApiResponse<SubfolderResponse> createSubfolder(@RequestBody @Valid SubfolderRequest request){
        return ApiResponse.<SubfolderResponse>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(subfolderService.createSubfolder(request))
                .build();
    }

    @Operation(summary = "Get Subfolders", description = "Get All Subfolders")
    @GetMapping("/getall")
    public ApiResponse<List<SubfolderResponse>> getAllSubfolders(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<SubfolderResponse>>builder()
                .entity(subfolderService.getAllSubfolders(page, perPage))
                .build();
    }

    @Operation(summary = "Get Subfolder", description = "Get A Subfolder by ID")
    @GetMapping("/get/{id}")
    public ApiResponse<SubfolderResponse> getSubfolderById(@PathVariable UUID id){
        return ApiResponse.<SubfolderResponse>builder()
                .entity(subfolderService.findSubfolderById(id))
                .build();
    }

    @Operation(summary = "Update Subfolder", description = "Update Subfolder by ID")
    @PutMapping("/update/{id}")
    public ApiResponse<SubfolderResponse> updateSubfolderById(@PathVariable UUID id, SubfolderUpdateRequest request){
        return ApiResponse.<SubfolderResponse>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(subfolderService.updateSubfolderById(id, request))
                .build();
    }

    @Operation(summary = "Delete Subfolder", description = "Delete Subfolder by ID")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<SubfolderResponse> deleteSubfolderById(@PathVariable UUID id){
        var subfolder = subfolderService.findSubfolderById(id);
        subfolderService.deleteSubfolderById(id);
        return ApiResponse.<SubfolderResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(subfolder)
                .build();
    }

    @Operation(summary = "Get Subfolders", description = "Get Subfolders by Project ID")
    @GetMapping(path = "/get/project/{projectId}")
    public ApiResponse<List<SubfolderResponse>> getSubfoldersByProjectId(@RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "10") int perPage,
                                                                         @PathVariable UUID projectId){
        return ApiResponse.<List<SubfolderResponse>>builder()
                .entity(subfolderService.getSubfoldersByProjectId(page, perPage, projectId))
                .build();
    }
}
