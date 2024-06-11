package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DepartmentRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectChangeStatusRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
@Slf4j
public class ProjectController {
    final ProjectService projectService;

    @Operation(summary = "Create New Project", description = "Create A Brand New Project")
    @PostMapping(path = "/create")
    public ApiResponse<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request){
        return ApiResponse.<ProjectResponse>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(projectService.createProject(request))
                .build();
    }

    @Operation(summary = "Get All Projects", description = "Get All Projects")
    @GetMapping(path = "/getall")
    public ApiResponse<List<ProjectResponse>> getAllProjects(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<ProjectResponse>>builder()
                .entity(projectService.getAllProjects(page, perPage))
                .build();
    }

    @Operation(summary = "Get Project", description = "Get A Project by ID")
    @GetMapping(path = "/get/{id}")
    public ApiResponse<ProjectResponse> getProjectById(@PathVariable UUID id){
        return ApiResponse.<ProjectResponse>builder()
                .entity(projectService.findProjectById(id))
                .build();
    }

    @Operation(summary = "Change Status", description = "Change A Project Status by ID")
    @PutMapping(path = "/change")
    public ApiResponse<ProjectResponse> changeProjectStatus(@RequestBody ProjectChangeStatusRequest request){
        return ApiResponse.<ProjectResponse>builder()
                .message(SuccessReturnMessage.CHANGE_SUCCESS.getMessage())
                .entity(projectService.changeProjectStatus(request))
                .build();
    }

    @Operation(summary = "Update Project", description = "Update A Project by ID")
    @PutMapping(path = "/update/{id}")
    public ApiResponse<ProjectResponse> updateProjectById(@PathVariable UUID id, @RequestBody @Valid ProjectUpdateRequest request){
        return ApiResponse.<ProjectResponse>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(projectService.updateProjectById(id, request))
                .build();
    }

    @Operation(summary = "Delete Project", description = "Delete A Project by ID")
    @DeleteMapping(path = "/delete/{id}")
    public ApiResponse<ProjectResponse> deleteProjectById(@PathVariable UUID id){
        var projectResponse = projectService.findProjectById(id);
        projectService.deleteProjectById(id);
        return ApiResponse.<ProjectResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(projectResponse)
                .build();
    }

    @Operation(summary = "Find Projects", description = "Find Project(s) by Name")
    @GetMapping(path = "/search")
    public ApiResponse<List<ProjectResponse>> searchProjectsByName(@NotBlank String name,
                                                                   @RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<ProjectResponse>>builder()
                .entity(projectService.findProjectByNameContaining(name, page, perPage))
                .build();
    }
}
