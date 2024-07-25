package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DrawingResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.DrawingStatus;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.DrawingService;
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
@RequestMapping("/drawing")
@Slf4j
public class DrawingController {
    final DrawingService drawingService;

    @Operation(summary = "Create New Drawing", description = "Create A Brand New Drawing")
    @PostMapping(path = "/create")
    public ApiResponse<DrawingResponse> createDrawing(@RequestBody @Valid DrawingRequest request){
        return ApiResponse.<DrawingResponse>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(drawingService.createDrawing(request))
                .build();
    }

    @Operation(summary = "Get All Drawings", description = "Get All Drawings By Status")
    @GetMapping(path = "/getallByStatus")
    public ApiResponse<List<DrawingResponse>> getAllDrawingsByStatus(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int perPage,
                                                             @RequestParam DrawingStatus status){
        return ApiResponse.<List<DrawingResponse>>builder()
                .entity(drawingService.getAllDrawingsByStatus(page, perPage, status))
                .build();
    }

    @Operation(summary = "Get All Drawings")
    @GetMapping(path = "/getall")
    public ApiResponse<List<DrawingResponse>> getAllDrawings(@RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<DrawingResponse>>builder()
                .entity(drawingService.getAllDrawings(page, perPage))
                .build();
    }

    @Operation(summary = "Find Drawings", description = "Find Drawing(s) by Name and Status")
    @GetMapping(path = "/searchByNameAndStatus")
    public ApiResponse<List<DrawingResponse>> searchDrawingsByNameAndStatus(@NotBlank String name,
                                                                             @RequestParam(defaultValue = "1") int page,
                                                                             @RequestParam(defaultValue = "10") int perPage,
                                                                             @RequestParam DrawingStatus status){
        return ApiResponse.<List<DrawingResponse>>builder()
                .entity(drawingService.findDrawingsByNameContainingAndStatus(name, status, page, perPage))
                .build();
    }

    @Operation(summary = "Find Drawings", description = "Find Drawing(s) by Name")
    @GetMapping(path = "/searchByName")
    public ApiResponse<List<DrawingResponse>> searchDrawingsByName(@NotBlank String name,
                                                                            @RequestParam(defaultValue = "1") int page,
                                                                            @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<DrawingResponse>>builder()
                .entity(drawingService.findDrawingsByNameContaining(name, page, perPage))
                .build();
    }


    @Operation(summary = "Find Drawings", description = "Find Drawing(s) by Folder and Status")
    @GetMapping(path = "/searchByFolderAndStatus/{folderId}")
    public ApiResponse<List<DrawingResponse>> searchDrawingByFolderAndStatus(@PathVariable UUID folderId,
                                                                         @RequestParam DrawingStatus status,
                                                                         @RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<DrawingResponse>>builder()
                .entity(drawingService.findDrawingsByFolderAndStatus(folderId, status, page, perPage))
                .build();
    }

    @Operation(summary = "Find Drawings", description = "Find Drawing(s) by Folder")
    @GetMapping(path = "/searchByFolder/{folderId}")
    public ApiResponse<List<DrawingResponse>> searchDrawingByFolder(@PathVariable UUID folderId,
                                                                             @RequestParam(defaultValue = "1") int page,
                                                                             @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<DrawingResponse>>builder()
                .entity(drawingService.findDrawingsByFolder(folderId, page, perPage))
                .build();
    }

    @Operation(summary = "Get Processing Drawings", description = "Get Processing Drawings By Folder ID")
    @GetMapping(path = "/searchByFolder/processing/{folderId}")
    public ApiResponse<List<DrawingResponse>> getProcessingDrawingsByFolderId(@RequestParam(defaultValue = "1") int page,
                                                                              @RequestParam(defaultValue = "10") int perPage,
                                                                              @PathVariable UUID folderId){
        return ApiResponse.<List<DrawingResponse>>builder()
                .entity(drawingService.findProcessingDrawingsByFolder(folderId, page, perPage))
                .build();
    }

    @Operation(summary = "Get Processing Drawings", description = "Get Processing Drawings By Project ID")
    @GetMapping(path = "/searchByProject/processing/{projectId}")
    public ApiResponse<List<DrawingResponse>> getProcessingDrawingsByProjectId(@RequestParam(defaultValue = "1") int page,
                                                                              @RequestParam(defaultValue = "10") int perPage,
                                                                              @PathVariable UUID projectId){
        return ApiResponse.<List<DrawingResponse>>builder()
                .entity(drawingService.findProcessingDrawingsByProject(projectId, page, perPage))
                .build();
    }

    @Operation(summary = "Get Drawing", description = "Get A Drawing by ID")
    @GetMapping(path = "/get/{id}")
    public ApiResponse<DrawingResponse> getDrawingById(@PathVariable UUID id){
        return ApiResponse.<DrawingResponse>builder()
                .entity(drawingService.findDrawingById(id))
                .build();
    }

    @Operation(summary = "Get Drawing", description = "Get A Drawing by ID and Status")
    @GetMapping(path = "/getByStatus/{id}")
    public ApiResponse<DrawingResponse> getDrawingByIdAndStatus(@PathVariable UUID id,
                                                                @RequestParam DrawingStatus status){
        return ApiResponse.<DrawingResponse>builder()
                .entity(drawingService.findDrawingByIdAndStatus(id, status))
                .build();
    }

    @Operation(summary = "Change Status", description = "Change A Drawing Status by ID")
    @PutMapping(path = "/change/status")
    public ApiResponse<DrawingResponse> changeDrawingStatus(@RequestBody @Valid DrawingStatusChangeRequest request,
                                                            @RequestParam DrawingStatus status){
        return ApiResponse.<DrawingResponse>builder()
                .message(SuccessReturnMessage.CHANGE_SUCCESS.getMessage())
                .entity(drawingService.changeDrawingStatus(request, status))
                .build();
    }

    @Operation(summary = "Delete Drawing", description = "Change A Drawing Status To Inactive by ID")
    @PutMapping(path = "/change/status/{drawingId}")
    public ApiResponse<DrawingResponse> changeDrawingToHiddenStatus(@PathVariable UUID drawingId){
        return ApiResponse.<DrawingResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(drawingService.changeDrawingToInactiveStatus(drawingId))
                .build();
    }

    @Operation(summary = "Update Drawing", description = "Update A Drawing by ID")
    @PutMapping(path = "/update/{drawingId}")
    public ApiResponse<DrawingResponse> updateDrawingById(@PathVariable UUID drawingId,
                                                          @RequestBody @Valid DrawingUpdateRequest request){
        return ApiResponse.<DrawingResponse>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(drawingService.updateDrawingById(drawingId, request))
                .build();
    }

    @Operation(summary = "Delete Drawing", description = "Delete A Drawing by ID")
    @DeleteMapping(path = "/delete/{drawingId}")
    public ApiResponse<DrawingResponse> deleteDrawingById(@PathVariable UUID drawingId){
        var drawingResponse = drawingService.findDrawingById(drawingId);
        drawingService.deleteDrawingById(drawingId);
        return ApiResponse.<DrawingResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(drawingResponse)
                .build();
    }
}
