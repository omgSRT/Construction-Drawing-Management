package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FloorDetailCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FloorDetailUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.FloorDetailResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.FloorDetailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/floorDetail")
@Slf4j
public class FloorDetailController {
    final FloorDetailService floorDetailService;

    @Operation(summary = "Create Floor Detail", description = "Create A Brand New Floor Detail")
    @PostMapping(path = "/create")
    public ApiResponse<FloorDetailResponse> createFloorDetail(@RequestBody @Valid FloorDetailCreateRequest request){
        return ApiResponse.<FloorDetailResponse>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(floorDetailService.createFloorDetail(request))
                .build();
    }

    @Operation(summary = "Get All Floor Detail")
    @GetMapping(path = "/getall")
    public ApiResponse<List<FloorDetailResponse>> getAllFloorDetails(@RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<FloorDetailResponse>>builder()
                .entity(floorDetailService.getAllFloorDetails(page, perPage))
                .build();
    }

    @Operation(summary = "Get All Floor Detail By Project ID")
    @GetMapping(path = "/getall/{projectId}")
    public ApiResponse<List<FloorDetailResponse>> getAllFloorDetailsByProjectId(@RequestParam(defaultValue = "1") int page,
                                                                                @RequestParam(defaultValue = "10") int perPage,
                                                                                @PathVariable UUID projectId){
        return ApiResponse.<List<FloorDetailResponse>>builder()
                .entity(floorDetailService.getAllFloorDetailsByProjectId(page, perPage, projectId))
                .build();
    }

    @Operation(summary = "Get Floor Detail", description = "Get Floor Detail By ID")
    @GetMapping(path = "/get/{floorDetailId}")
    public ApiResponse<FloorDetailResponse> getFloorDetailById(@PathVariable UUID floorDetailId){
        return ApiResponse.<FloorDetailResponse>builder()
                .entity(floorDetailService.getFloorDetailById(floorDetailId))
                .build();
    }

    @Operation(summary = "Delete Floor Detail", description = "Delete Floor Detail By ID")
    @DeleteMapping(path = "/delete/{floorDetailId}")
    public ApiResponse<FloorDetailResponse> deleteFloorDetailById(@PathVariable UUID floorDetailId){
        return ApiResponse.<FloorDetailResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(floorDetailService.deleteFloorDetailById(floorDetailId))
                .build();
    }

    @Operation(summary = "Update Floor Detail", description = "Update Floor Detail By ID")
    @PutMapping(path = "/update/{floorDetailId}")
    public ApiResponse<FloorDetailResponse> updateFloorDetailById(@PathVariable UUID floorDetailId,
                                                                  @RequestBody @Valid FloorDetailUpdateRequest request){
        return ApiResponse.<FloorDetailResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(floorDetailService.updateFloorDetailById(floorDetailId, request))
                .build();
    }
}
