package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.*;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Permission;
import com.GSU24SE43.ConstructionDrawingManagement.enums.TaskStatus;
import com.GSU24SE43.ConstructionDrawingManagement.service.TaskService;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public ApiResponse<TaskParentCreateResponse> createTaskParentByAdmin(@RequestBody TaskParentCreateRequest request) {
        return ApiResponse.<TaskParentCreateResponse>builder()
                .entity(taskService.createTaskParentByAdmin(request))
                .build();
    }


    @PostMapping("/createTaskChildByAdmin_V2/{parentTaskId}")
    public ApiResponse<TaskChildCreateResponse> createChildTaskByAdmin_V2(@PathVariable UUID parentTaskId, @RequestBody TaskChildCreateRequest_V2 request) {
        return ApiResponse.<TaskChildCreateResponse>builder()
                .entity(taskService.createChildTaskByAdmin_V2(parentTaskId, request))
                .build();
    }

    @Operation(summary = "Create task parent by head", description = "Create task parent by head")
    @PostMapping("/createTaskParentByHead")
    public ApiResponse<TaskParentCreateByHeadResponse> createTaskParentByHead(@RequestBody TaskParentCreateByHeadRequest request) {
        return ApiResponse.<TaskParentCreateByHeadResponse>builder()
                .entity(taskService.createTaskParentByHead(request))
                .build();
    }



//    @Operation(summary = "Update status task parent ", description = "Update status task parent")
//    @PostMapping("/updateStatusTaskParent/{parentTaskId}")
//    public ApiResponse<TaskParentUpdateByAdminResponse> updateStatusParentTask(@PathVariable UUID parentTaskId, @RequestParam TaskStatus status) {
//        return ApiResponse.<TaskParentUpdateByAdminResponse>builder()
//                .entity(taskService.updateStatusTaskParent(parentTaskId, status))
//                .build();
//    }
//
//    @Operation(summary = "Update status task child ", description = "Update status task child")
//    @PostMapping("/updateStatusTaskChild/{childTaskId}")
//    public ApiResponse<TaskChildUpdateByAdminResponse> updateStatusChildTask(@PathVariable UUID childTaskId, @RequestParam TaskStatus status) {
//        return ApiResponse.<TaskChildUpdateByAdminResponse>builder()
//                .entity(taskService.updateStatusTaskChild(childTaskId, status))
//                .build();
//    }
//    @Operation(summary = "Update status task child by admin", description = "Bản update premium của update task child by admin")
//    @PostMapping("/updateStatusChildByAdmin/{childTaskId}")
//    public ApiResponse<TaskChildUpdateByAdminResponse> updateStatusChildTaskByAdmin(@PathVariable UUID childTaskId, @RequestParam TaskStatus status) {
//        return ApiResponse.<TaskChildUpdateByAdminResponse>builder()
//                .entity(taskService.upgradeStatus(childTaskId, status))
//                .build();
//    }

    @PostMapping("/updateStatusChild_V2/{childTaskId}")
    public ApiResponse<TaskChildUpdateByAdminResponse> updateStatusChildTaskByAdmin2(@PathVariable UUID childTaskId, @RequestParam TaskStatus status) {
        return ApiResponse.<TaskChildUpdateByAdminResponse>builder()
                .entity(taskService.upgradeStatus_3(childTaskId, status))
                .build();
    }

    @Operation(summary = "Update task parent by admin", description = "Update task parent by admin")
    @PostMapping("/updateStatusTaskParentByAdmin/{parentTaskId}")
    public ApiResponse<TaskParentUpdateByAdminResponse> updateTaskParentByAdmin(@PathVariable UUID parentTaskId, @RequestBody TaskParentUpdateByAdminRequest request) {
        return ApiResponse.<TaskParentUpdateByAdminResponse>builder()
                .entity(taskService.updateTaskParentByAdmin(parentTaskId, request))
                .build();
    }


    @PostMapping("/createTaskChildByHead_V2/{parentTaskId}")
    public ApiResponse<TaskChildCreateByHeadResponse> v2(@PathVariable UUID parentTaskId, @RequestBody TaskChildCreateByHead_V2Request request){
        return ApiResponse.<TaskChildCreateByHeadResponse>builder()
                .entity(taskService.createTaskChildByHead_LHNH(parentTaskId, request))
                .build();
    }

    @Operation(summary = "Get all task", description = "Get all task")
    @GetMapping("/getAll")
    public ApiResponse<List<Task>> getAll() {
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.getAll())
                .build();
    }

    @Operation(summary = "Get all task", description = "Get all permission")
    @GetMapping("/getAllPermission")
    public ApiResponse<List<Permission>> getAllPermission() {
        return ApiResponse.<List<Permission>>builder()
                .entity(taskService.getAllPermission())
                .build();
    }

    @Operation(summary = "Get all parent task", description = "Get all parent task")
    @GetMapping("/getAllParentTask")
    public ApiResponse<List<Task>> getAllParentTask() {
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.getAllParentTask())
                .build();
    }
    @Operation(summary = "Get All task parent of admin", description = "Get All task parent of admin")
    @GetMapping("/getAllParentTaskOfAdmin")
    public ApiResponse<List<Task>> getAllParentTaskOfAdmin() {
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.getAllParentTaskOfAdmin())
                .build();
    }

    @Operation(summary = "Get All task parent of projectId", description = "Get All task parent of admin")
    @GetMapping("/getAllParentTaskByProjectId/{projectId}")
    public ApiResponse<List<Task>> getAllParentTaskByProject(@PathVariable UUID projectId) {
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.getParentTaskByProjectId(projectId))
                .build();
    }

    @Operation(summary = "Get All task child of admin", description = "Get All task child of admin")
    @GetMapping("/getAllChildTaskOfAdmin")
    public ApiResponse<List<Task>> getAllChildTaskOfAdmin() {
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.getAllChildTasksOfAdmin())
                .build();
    }
    @Operation(summary = "Get All task parent of head", description = "Get All task parent of head")
    @GetMapping("/getAllParentTaskOfHead")
    public ApiResponse<List<Task>> getAllParentTaskOfHead() {
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.getAllParentTaskOfHead())
                .build();
    }
    @Operation(summary = "Get All task child of head", description = "Get All task child of head")
    @GetMapping("/getAllChildTaskOfHead")
    public ApiResponse<List<Task>> getAllChildTaskOfHead() {
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.getAllChildTaskOfHead())
                .build();
    }
    @Operation(summary = "Get All task of designer, head", description = "Get All task of designer")
    @GetMapping("/getAllTaskOfDesigner")
    public ApiResponse<List<TaskResponseDesigner>> getAllTaskOfDesigner() {
        return ApiResponse.<List<TaskResponseDesigner>>builder()
                .entity(taskService.getAllTaskOfDesigner())
                .build();
    }

    @Operation(summary = "Get All task of designer, head", description = "Get All task of designer")
    @GetMapping("/getAllTaskOfHeadFromAdmin")
    public ApiResponse<List<TaskResponse>> getAllTaskOfHead() {
        return ApiResponse.<List<TaskResponse>>builder()
                .entity(taskService.getAllTaskOfHead())
                .build();
    }

//    @Operation(summary = "Search task by id", description = "Search task by id")
//    @GetMapping("/findTaskById")
//    public ApiResponse<Task> findTask(@RequestParam UUID taskId){
//        return ApiResponse.<Task>builder()
//                .entity(taskService.findTaskById(taskId))
//                .build();
//    }
    @Operation(summary = "Filter task", description = "Filter task")
    @GetMapping("/filterTask")
    public ApiResponse<List<Task>> filterTask(@RequestParam(required = false) UUID taskId
            ,@RequestParam(required = false) String status
            ,@RequestParam(required = false) String title
            ,@RequestParam(required = false) Date beginDate
            ,@RequestParam(required = false) Date endDate){
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.filterTask(taskId, title, status,beginDate, endDate))
                .build();
    }
    @Operation(summary = "Get all child task of a task parent", description = "Get all child task of a task parent")
    @GetMapping("/getChildTaskOfAParentTask/{parentTaskId}")
    public ApiResponse<List<Task>> getChildTaskOfAParentTask(@PathVariable UUID parentTaskId){
        return ApiResponse.<List<Task>>builder()
                .entity(taskService.getChildTaskOfAParentTask(parentTaskId))
                .build();
    }

    @DeleteMapping("/{taskId}")
    public ApiResponse<Void> deleteTask(@PathVariable UUID taskId){
        taskService.deleteTask(taskId);
        return ApiResponse.<Void>builder()
                .message("Delete Task Success")
                .build();
    }





}
