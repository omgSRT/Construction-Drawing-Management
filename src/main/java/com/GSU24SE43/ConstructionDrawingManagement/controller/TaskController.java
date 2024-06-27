package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskChildCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskParentCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskChildCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskParentCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import com.GSU24SE43.ConstructionDrawingManagement.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskService taskService;

    @PostMapping("/create")
    public ApiResponse<TaskParentCreateResponse> createTaskParentByAdmin(@RequestBody TaskParentCreateRequest request){
        return ApiResponse.<TaskParentCreateResponse>builder()
                .entity(taskService.createTaskParentByAdmin(request))
                .build();
    }
    @PostMapping("/{parentTaskId}")
    public ApiResponse<TaskChildCreateResponse> createChildTaskByAdmin(@PathVariable UUID parentTaskId, @RequestBody TaskChildCreateRequest request){
        return ApiResponse.<TaskChildCreateResponse>builder()
                .entity(taskService.createChildTaskByAdmin(parentTaskId,request))
                .build();
    }
    @GetMapping("/getAll")
    public ApiResponse<List<Task>> getAll(){
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.getAll())
                .build();
    }
    @GetMapping("/getAllParentTask")
    public ApiResponse<List<Task>> getAllParentTask(){
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.getAllParentTask())
                .build();
    }
}
