package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DetailTaskCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DetailTaskUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.service.DetailTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/detail-task")
@Slf4j
public class DetailTaskController {
    @Autowired
    DetailTaskService detailTaskService;

    @PostMapping("/create-by-head")
    public ApiResponse<DetailTaskCreateResponse> createDetailTaskByHead(@RequestBody DetailTaskCreateRequest request){
        return ApiResponse.<DetailTaskCreateResponse>builder()
                .entity(detailTaskService.createDetailTask(request))
                .build();
    }

    @PostMapping("/update-by-head/{detailTaskId}")
    public ApiResponse<DetailTaskUpdateResponse> createDetailTaskByHead(@PathVariable UUID detailTaskId, @RequestBody DetailTaskUpdateRequest request){
        return ApiResponse.<DetailTaskUpdateResponse>builder()
                .entity(detailTaskService.detailTaskParentUpdate(detailTaskId, request))
                .build();
    }

    @GetMapping("/getAll")
    public ApiResponse<List<DetailTaskResponse>> createDetailTaskByHead(){
        return ApiResponse.<List<DetailTaskResponse>>builder()
                .entity(detailTaskService.getAll())
                .build();
    }

    @DeleteMapping("/{detailTaskId}")
    public ApiResponse<Void> createDetailTaskByHead(@PathVariable UUID detailTaskId){
        detailTaskService.delete(detailTaskId);
        return ApiResponse.<Void>builder()
                .message("")
                .build();
    }





}
