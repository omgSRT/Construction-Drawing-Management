package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccessUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.LogCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.service.LogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogController {
    LogService logService;

    @PostMapping("/create")
    public ApiResponse<AccessCreateResponse> create(@RequestBody LogCreateRequest request){
        return ApiResponse.<AccessCreateResponse>builder()
                .entity(logService.create(request))
                .build();
    }
    @GetMapping("/getAll")
    public ApiResponse<List<AccessResponse>> getAll(){
        return ApiResponse.<List<AccessResponse>>builder()
                .entity(logService.getAll())
                .build();
    }
    @GetMapping("/{logId}")
    public ApiResponse<AccessResponse> getAccess(@PathVariable UUID logId){
        return ApiResponse.<AccessResponse>builder()
                .entity(logService.getById(logId))
                .build();
    }
    @PutMapping("/{logId}")
    public ApiResponse<AccessUpdateResponse> update(@PathVariable UUID logId, @RequestBody AccessUpdateRequest request){
        return ApiResponse.<AccessUpdateResponse>builder()
                .entity(logService.updateLog(logId,request))
                .build();
    }
    @DeleteMapping("/{logId}")
    public ApiResponse<Void> delete(@PathVariable UUID logId){
        logService.delete(logId);
        return ApiResponse.<Void>builder()
                .message("Delete success")
                .build();
    }


}
