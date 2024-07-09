package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.*;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import com.GSU24SE43.ConstructionDrawingManagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskService taskService;


    @Operation(summary = "Create task parent by admin", description = "Create task parent by admin")
    @PostMapping("/createTaskParentByAdmin")
    public ApiResponse<TaskParentCreateResponse> createTaskParentByAdmin(@RequestBody TaskParentCreateRequest request){
        return ApiResponse.<TaskParentCreateResponse>builder()
                .entity(taskService.createTaskParentByAdmin(request))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create task child by admin", description = "Create task child by admin")
    @PostMapping("/createTaskChildByAdmin/{parentTaskId}")
    public ApiResponse<TaskChildCreateResponse> createChildTaskByAdmin(@PathVariable UUID parentTaskId, @RequestBody TaskChildCreateRequest request){
        return ApiResponse.<TaskChildCreateResponse>builder()
                .entity(taskService.createChildTaskByAdmin(parentTaskId,request))
                .build();
    }
    @Operation(summary = "Create task parent by head", description = "Create task parent by head")
    @PostMapping("/createTaskParentByHead")
    public ApiResponse<TaskParentCreateByHeadResponse> createTaskParentByHead(@RequestBody TaskParentCreateByHeadRequest request){
        return ApiResponse.<TaskParentCreateByHeadResponse>builder()
                .entity(taskService.createTaskParentByHead(request))
                .build();
    }
    @Operation(summary = "Create task child by head", description = "Create task child by head")
    @PostMapping("/createTaskChildByHead/{parentTaskId}")
    public ApiResponse<TaskChildCreateByHeadResponse> createChildTaskByHead(@PathVariable UUID parentTaskId, @RequestBody TaskChildCreateByHeadRequest request){
        return ApiResponse.<TaskChildCreateByHeadResponse>builder()
                .entity(taskService.createTaskChildByHead(parentTaskId,request))
                .build();
    }
    @Operation(summary = "Update status task parent ", description = "Update status task parent")
    @PostMapping("/updateStatusTaskParent/{parentTaskId}")
    public ApiResponse<TaskParentUpdateByAdminResponse> updateStatusParentTask(@PathVariable UUID parentTaskId, @RequestParam String status){
        return ApiResponse.<TaskParentUpdateByAdminResponse>builder()
                .entity(taskService.updateStatusTaskParent(parentTaskId,status))
                .build();
    }
    @Operation(summary = "Update status task child ", description = "Update status task child")
    @PostMapping("/updateStatusTaskChild/{childTaskId}")
    public ApiResponse<TaskChildUpdateByAdminResponse> updateStatusChildTask(@PathVariable UUID childTaskId, @RequestParam String status){
        return ApiResponse.<TaskChildUpdateByAdminResponse>builder()
                .entity(taskService.updateStatusTaskChild(childTaskId,status))
                .build();
    }
    @Operation(summary = "Update task parent by admin", description = "Update task parent by admin")
    @PostMapping("/updateStatusTaskParentByAdmin/{parentTaskId}")
    public ApiResponse<TaskParentUpdateByAdminResponse> updateTaskParentByAdmin(@PathVariable UUID parentTaskId,@RequestBody TaskParentUpdateByAdminRequest request){
        return ApiResponse.<TaskParentUpdateByAdminResponse>builder()
                .entity(taskService.updateTaskParentByAdmin(parentTaskId, request))
                .build();
    }
    @Operation(summary = "Update task child by admin", description = "Update task child by admin")
    @PostMapping("/updateStatusTaskChildByAdmin/{parentTaskId}")
    public ApiResponse<TaskChildUpdateByAdminResponse> updateTaskChildByAdmin(@PathVariable UUID parentTaskId, @RequestBody TaskChildUpdateByAdminRequest request){
        return ApiResponse.<TaskChildUpdateByAdminResponse>builder()
                .entity(taskService.updateTaskChildByAdmin(parentTaskId, request))
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
