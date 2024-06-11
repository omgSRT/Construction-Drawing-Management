package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.FolderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/folder")
@Slf4j
public class FolderController {
    private final FolderService folderService;

    @Operation(summary = "Create New Folder", description = "Create A Brand New Folder - Admin")
    @PostMapping(path = "/create")
    public ApiResponse<Folder> createFolder(@RequestBody @Valid FolderRequest request){
        return ApiResponse.<Folder>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(folderService.createFolder(request))
                .build();
    }

    @Operation(summary = "Get All Folders", description = "Get All Folders - Admin")
    @GetMapping(path = "/getall")
    public ApiResponse<List<Folder>> getAllFolders(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<Folder>>builder()
                .entity(folderService.getAllFolders(page, perPage))
                .build();
    }

    @Operation(summary = "Get Folder", description = "Get A Folder by ID")
    @GetMapping(path = "/get/{id}")
    public ApiResponse<Folder> getFolderById(@PathVariable UUID id){
        return ApiResponse.<Folder>builder()
                .message(SuccessReturnMessage.SEARCH_SUCCESS.getMessage())
                .entity(folderService.findFolderById(id))
                .build();
    }

    @Operation(summary = "Update Folder", description = "Update A Folder by ID")
    @PutMapping(path = "/update/{id}")
    public ApiResponse<Folder> UpdateFolderById(@PathVariable UUID id, @RequestBody @Valid FolderRequest request){
        return ApiResponse.<Folder>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(folderService.updateFolderById(id, request))
                .build();
    }

    @Operation(summary = "Delete Folder", description = "Delete Folder by ID")
    @DeleteMapping(path = "/delete/{id}")
    public ApiResponse<Folder> deleteFolder(@PathVariable UUID id){
        Folder folder = folderService.findFolderById(id);
        folderService.deleteFolderById(id);
        return ApiResponse.<Folder>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(folder)
                .build();
    }

    @Operation(summary = "Find Folders", description = "Find Folder(s) by Folder's Name")
    @GetMapping(path = "/search")
    public ApiResponse<List<Folder>> searchFoldersByName(@NotBlank String name,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<Folder>>builder()
                .entity(folderService.findFolderByNameContaining(name, page, perPage))
                .build();
    }
}
