package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.FolderResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.service.FolderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/folder")
@Slf4j
public class FolderController {
    @Autowired
    FolderService folderService;

    @Operation(summary = "Create New Folder", description = "Create A Brand New Folder - Admin")
    @PostMapping(path = "/create")
    public ApiResponse<FolderResponse> createFolder(@RequestBody @Valid FolderRequest request){
        return ApiResponse.<FolderResponse>builder()
                .message("Create Successfully")
                .entity(folderService.createFolder(request))
                .build();
    }

    @Operation(summary = "Get All Folders", description = "Get All Folders - Admin")
    @GetMapping(path = "/getall")
    public ApiResponse<List<FolderResponse>> getAllFolders(){
        return ApiResponse.<List<FolderResponse>>builder()
                .entity(folderService.getAllFolders())
                .build();
    }

    @Operation(summary = "Get Folder", description = "Get A Folder by ID")
    @GetMapping(path = "/get/{id}")
    public ApiResponse<Folder> getFolderById(@PathVariable UUID id){
        return ApiResponse.<Folder>builder()
                .entity(folderService.findFolderById(id))
                .build();
    }

    @Operation(summary = "Update Folder", description = "Update A Folder by ID")
    @PutMapping(path = "/update/{id}")
    public ApiResponse<FolderResponse> UpdateFolderById(@PathVariable UUID id, @RequestBody @Valid FolderRequest request){
        return ApiResponse.<FolderResponse>builder()
                .entity(folderService.updateFolderById(id, request))
                .build();
    }

    @Operation(summary = "Delete Folder", description = "Delete Folder by ID")
    @DeleteMapping(path = "/delete/{id}")
    public ApiResponse<Folder> deleteFolder(@PathVariable UUID id){
        Folder folder = folderService.findFolderById(id);
        folderService.deleteFolderById(id);
        return ApiResponse.<Folder>builder()
                .message("Delete Successfully")
                .entity(folder)
                .build();
    }
}
