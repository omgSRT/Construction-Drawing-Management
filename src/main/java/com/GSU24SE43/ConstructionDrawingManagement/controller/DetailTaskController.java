package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DetailTaskCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.service.DetailTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                .entity(detailTaskService.createDetailTaskByHead(request))
                .build();
    }

}
