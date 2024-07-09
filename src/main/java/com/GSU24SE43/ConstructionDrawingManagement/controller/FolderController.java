package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.FolderResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.FolderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/folder")
@Slf4j
public class FolderController {
    final FolderService FolderService;

    @Operation(summary = "Create Folder", description = "Create A Brand New Folder")
    @PostMapping("/create")
    public ApiResponse<FolderResponse> createFolder(@RequestBody @Valid FolderRequest request){
        return ApiResponse.<FolderResponse>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(FolderService.createFolder(request))
                .build();
    }

    @Operation(summary = "Get Folders", description = "Get All Folders")
    @GetMapping("/getall")
    public ApiResponse<List<FolderResponse>> getAllFolders(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<FolderResponse>>builder()
                .entity(FolderService.getAllFolders(page, perPage))
                .build();
    }

    @Operation(summary = "Get Folder", description = "Get A Folder by ID")
    @GetMapping("/get/{id}")
    public ApiResponse<FolderResponse> getFolderById(@PathVariable UUID id){
        return ApiResponse.<FolderResponse>builder()
                .entity(FolderService.findFolderById(id))
                .build();
    }

    @Operation(summary = "Update Folder", description = "Update Folder by ID")
    @PutMapping("/update/{id}")
    public ApiResponse<FolderResponse> updateFolderById(@PathVariable UUID id, FolderUpdateRequest request){
        return ApiResponse.<FolderResponse>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(FolderService.updateFolderById(id, request))
                .build();
    }

    @Operation(summary = "Delete Folder", description = "Delete Folder by ID")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<FolderResponse> deleteFolderById(@PathVariable UUID id){
        var Folder = FolderService.findFolderById(id);
        FolderService.deleteFolderById(id);
        return ApiResponse.<FolderResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(Folder)
                .build();
    }

    @Operation(summary = "Get Folders", description = "Get Folders by Project ID")
    @GetMapping(path = "/get/project/{projectId}")
    public ApiResponse<List<FolderResponse>> getFoldersByProjectId(@RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "10") int perPage,
                                                                         @PathVariable UUID projectId){
        return ApiResponse.<List<FolderResponse>>builder()
                .entity(FolderService.getFoldersByProjectId(page, perPage, projectId))
                .build();
    }

    @Operation(summary = "Get Folders", description = "Get Folders by Folder Name")
    @GetMapping(path = "/get/project/{name}")
    public ApiResponse<List<FolderResponse>> getFoldersByFolderName(@RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "10") int perPage,
                                                                   @PathVariable String name){
        return ApiResponse.<List<FolderResponse>>builder()
                .entity(FolderService.findFolderByNameContaining(name, page, perPage))
                .build();
    }
}
