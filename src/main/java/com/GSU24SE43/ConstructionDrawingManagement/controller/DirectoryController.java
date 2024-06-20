package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DirectoryRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Directory;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.DirectoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/directory")
@Slf4j
public class DirectoryController {
    private final DirectoryService directoryService;

    @Operation(summary = "Create New Directory", description = "Create A Brand New Directory")
    @PostMapping(path = "/create")
    public ApiResponse<Directory> createDirectory(@RequestBody @Valid DirectoryRequest request){
        return ApiResponse.<Directory>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(directoryService.createDirectory(request))
                .build();
    }

    @Operation(summary = "Get All Directories", description = "Get All Directories")
    @GetMapping(path = "/getall")
    public ApiResponse<List<Directory>> getAllDirectories(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<Directory>>builder()
                .entity(directoryService.getAllDirectories(page, perPage))
                .build();
    }

    @Operation(summary = "Get Directory", description = "Get A Directory by ID")
    @GetMapping(path = "/get/{id}")
    public ApiResponse<Directory> getDirectoryById(@PathVariable UUID id){
        return ApiResponse.<Directory>builder()
                .message(SuccessReturnMessage.SEARCH_SUCCESS.getMessage())
                .entity(directoryService.findDirectoryById(id))
                .build();
    }

    @Operation(summary = "Update Directory", description = "Update A Directory by ID")
    @PutMapping(path = "/update/{id}")
    public ApiResponse<Directory> updateDirectoryById(@PathVariable UUID id, @RequestBody @Valid DirectoryRequest request){
        return ApiResponse.<Directory>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(directoryService.updateDirectoryById(id, request))
                .build();
    }

    @Operation(summary = "Delete Directory", description = "Delete Directory by ID")
    @DeleteMapping(path = "/delete/{id}")
    public ApiResponse<Directory> deleteDirectory(@PathVariable UUID id){
        Directory directory = directoryService.findDirectoryById(id);
        directoryService.deleteDirectoryById(id);
        return ApiResponse.<Directory>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(directory)
                .build();
    }

    @Operation(summary = "Find Directories", description = "Find Directories by Directory's Name")
    @GetMapping(path = "/search")
    public ApiResponse<List<Directory>> searchDirectoriesByName(@NotBlank String DirectoryName,
                                                            @RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<Directory>>builder()
                .entity(directoryService.findDirectoryByNameContaining(DirectoryName, page, perPage))
                .build();
    }
}
