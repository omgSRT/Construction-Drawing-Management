package com.GSU24SE43.ConstructionDrawingManagement.controller;
//
//import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderRequest;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
//import com.GSU24SE43.ConstructionDrawingManagement.entity.Directory;
//import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
//import com.GSU24SE43.ConstructionDrawingManagement.service.DirectoryService;
//import io.swagger.v3.oas.annotations.Operation;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotBlank;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/folder")
//@Slf4j
//public class DirectoryController {
//    private final DirectoryService directoryService;
//
//    @Operation(summary = "Create New Folder", description = "Create A Brand New Folder - Admin")
//    @PostMapping(path = "/create")
//    public ApiResponse<Directory> createFolder(@RequestBody @Valid FolderRequest request){
//        return ApiResponse.<Directory>builder()
//                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
//                .entity(directoryService.createFolder(request))
//                .build();
//    }
//
//    @Operation(summary = "Get All Folders", description = "Get All Folders - Admin")
//    @GetMapping(path = "/getall")
//    public ApiResponse<List<Directory>> getAllFolders(@RequestParam(defaultValue = "1") int page,
//                                                      @RequestParam(defaultValue = "10") int perPage){
//        return ApiResponse.<List<Directory>>builder()
//                .entity(directoryService.getAllFolders(page, perPage))
//                .build();
//    }
//
//    @Operation(summary = "Get Folder", description = "Get A Folder by ID")
//    @GetMapping(path = "/get/{id}")
//    public ApiResponse<Directory> getFolderById(@PathVariable UUID id){
//        return ApiResponse.<Directory>builder()
//                .message(SuccessReturnMessage.SEARCH_SUCCESS.getMessage())
//                .entity(directoryService.findFolderById(id))
//                .build();
//    }
//
//    @Operation(summary = "Update Folder", description = "Update A Folder by ID")
//    @PutMapping(path = "/update/{id}")
//    public ApiResponse<Directory> updateFolderById(@PathVariable UUID id, @RequestBody @Valid FolderRequest request){
//        return ApiResponse.<Directory>builder()
//                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
//                .entity(directoryService.updateFolderById(id, request))
//                .build();
//    }
//
//    @Operation(summary = "Delete Folder", description = "Delete Folder by ID")
//    @DeleteMapping(path = "/delete/{id}")
//    public ApiResponse<Directory> deleteFolder(@PathVariable UUID id){
//        Directory directory = directoryService.findFolderById(id);
//        directoryService.deleteFolderById(id);
//        return ApiResponse.<Directory>builder()
//                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
//                .entity(directory)
//                .build();
//    }
//
//    @Operation(summary = "Find Folders", description = "Find Folder(s) by Folder's Name")
//    @GetMapping(path = "/search")
//    public ApiResponse<List<Directory>> searchFoldersByName(@NotBlank String folderName,
//                                                            @RequestParam(defaultValue = "1") int page,
//                                                            @RequestParam(defaultValue = "10") int perPage){
//        return ApiResponse.<List<Directory>>builder()
//                .entity(directoryService.findFolderByNameContaining(folderName, page, perPage))
//                .build();
//    }
//}
