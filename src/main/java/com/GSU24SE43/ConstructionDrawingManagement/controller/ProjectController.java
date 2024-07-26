//package com.GSU24SE43.ConstructionDrawingManagement.controller;
//
//import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectChangeStatusRequest;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectUpdateRequest;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
//import com.GSU24SE43.ConstructionDrawingManagement.enums.LandPurpose;
//import com.GSU24SE43.ConstructionDrawingManagement.enums.ProjectStatus;
//import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
//import com.GSU24SE43.ConstructionDrawingManagement.service.ProjectService;
//import io.swagger.v3.oas.annotations.Operation;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotBlank;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/project")
//@Slf4j
//public class ProjectController {
//    final ProjectService projectService;
//
//    @Operation(summary = "Create New Project", description = "Create A Brand New Project")
//    @PostMapping(path = "/create")
//    @Secured("ADMIN")
//
//    public ApiResponse<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request,
//                                                      @RequestParam LandPurpose landPurpose){
//        return ApiResponse.<ProjectResponse>builder()
//                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
//                .entity(projectService.createProject(request, landPurpose))
//                .build();
//    }
//
//    @Operation(summary = "Get All Projects", description = "Get All Projects")
//    @GetMapping(path = "/getall")
//    public ApiResponse<List<ProjectResponse>> getAllProjects(@RequestParam(defaultValue = "1") int page,
//                                                             @RequestParam(defaultValue = "10") int perPage){
//        return ApiResponse.<List<ProjectResponse>>builder()
//                .entity(projectService.getAllProjects(page, perPage))
//                .build();
//    }
//
//    @Operation(summary = "Get All Projects", description = "Get All Projects By Status")
//    @GetMapping(path = "/getallByStatus")
//    public ApiResponse<List<ProjectResponse>> getAllProjectsByStatus(@RequestParam(defaultValue = "1") int page,
//                                                             @RequestParam(defaultValue = "10") int perPage,
//                                                             @RequestParam ProjectStatus status){
//        return ApiResponse.<List<ProjectResponse>>builder()
//                .entity(projectService.getAllProjectsByStatus(page, perPage, status))
//                .build();
//    }
//
//    @Operation(summary = "Find Projects", description = "Find Project(s) by Department Name")
//    @GetMapping(path = "/search/departmentName")
//    public ApiResponse<List<ProjectResponse>> searchProjectsByDepartmentName(@NotBlank String departmentName,
//                                                                                       @RequestParam(defaultValue = "1") int page,
//                                                                                       @RequestParam(defaultValue = "10") int perPage){
//        return ApiResponse.<List<ProjectResponse>>builder()
//                .entity(projectService.findProjectByDepartmentName(departmentName, page, perPage))
//                .build();
//    }
//
//    @Operation(summary = "Find Projects", description = "Find Project(s) by Department Name with Status")
//    @GetMapping(path = "/search/departmentNameWithStatus")
//    public ApiResponse<List<ProjectResponse>> searchProjectsByDepartmentNameWithStatus(@NotBlank String departmentName,
//                                                                             @RequestParam(defaultValue = "1") int page,
//                                                                             @RequestParam(defaultValue = "10") int perPage,
//                                                                             @RequestParam ProjectStatus status){
//        return ApiResponse.<List<ProjectResponse>>builder()
//                .entity(projectService.findProjectByDepartmentNameAndStatus(departmentName, status, page, perPage))
//                .build();
//    }
//
//
//    @Operation(summary = "Find Projects", description = "Find Project(s) by Name with Status")
//    @GetMapping(path = "/search/projectNameWithStatus")
//    public ApiResponse<List<ProjectResponse>> searchProjectsByNameWithStatus(@NotBlank String projectName,
//                                                                         @RequestParam(defaultValue = "1") int page,
//                                                                         @RequestParam(defaultValue = "10") int perPage,
//                                                                         @RequestParam ProjectStatus status){
//        return ApiResponse.<List<ProjectResponse>>builder()
//                .entity(projectService.findProjectByNameContainingAndStatus(projectName, status, page, perPage))
//                .build();
//    }
//
//    @Operation(summary = "Find Projects", description = "Find Project(s) by Name")
//    @GetMapping(path = "/search/projectName")
//    public ApiResponse<List<ProjectResponse>> searchProjectsByName(@NotBlank String projectName,
//                                                                             @RequestParam(defaultValue = "1") int page,
//                                                                             @RequestParam(defaultValue = "10") int perPage){
//        return ApiResponse.<List<ProjectResponse>>builder()
//                .entity(projectService.findProjectByNameContaining(projectName, page, perPage))
//                .build();
//    }
//
//    @Operation(summary = "Get Project", description = "Get A Project by ID with Status")
//    @GetMapping(path = "/getWithStatus/{id}")
//    public ApiResponse<ProjectResponse> getProjectByIdAndStatus(@PathVariable UUID id,
//                                                       @RequestParam ProjectStatus status){
//        return ApiResponse.<ProjectResponse>builder()
//                .entity(projectService.findProjectByIdAndStatus(id, status))
//                .build();
//    }
//
//    @Operation(summary = "Get Project", description = "Get A Project by ID")
//    @GetMapping(path = "/get/{id}")
//    public ApiResponse<ProjectResponse> getProjectById(@PathVariable UUID id){
//        return ApiResponse.<ProjectResponse>builder()
//                .entity(projectService.findProjectById(id))
//                .build();
//    }
//
//    @Operation(summary = "Change Status", description = "Change A Project Status by ID")
//    @PutMapping(path = "/change")
//    public ApiResponse<ProjectResponse> changeProjectStatus(@RequestBody ProjectChangeStatusRequest request,
//                                                            @RequestParam ProjectStatus status){
//        return ApiResponse.<ProjectResponse>builder()
//                .message(SuccessReturnMessage.CHANGE_SUCCESS.getMessage())
//                .entity(projectService.changeProjectStatus(request, status))
//                .build();
//    }
//
//    @Operation(summary = "Update Project", description = "Update A Project by ID")
//    @PutMapping(path = "/update/{id}")
//    public ApiResponse<ProjectResponse> updateProjectById(@PathVariable UUID id,
//                                                          @RequestBody @Valid ProjectUpdateRequest request,
//                                                          @RequestParam LandPurpose landPurpose){
//        return ApiResponse.<ProjectResponse>builder()
//                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
//                .entity(projectService.updateProjectById(id, request, landPurpose))
//                .build();
//    }
//
//    @Operation(summary = "Delete Project", description = "Delete A Project by ID")
//    @DeleteMapping(path = "/delete/{id}")
//    public ApiResponse<ProjectResponse> deleteProjectById(@PathVariable UUID id){
//        return ApiResponse.<ProjectResponse>builder()
//                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
//                .entity(projectService.deleteProjectById(id))
//                .build();
//    }
//
//    @Operation(summary = "Delete Project", description = "Change A Project to Inactive Status by ID")
//    @PutMapping(path = "/change/{projectId}")
//    public ApiResponse<ProjectResponse> changeProjectToHiddenStatusById(@PathVariable UUID projectId){
//        return ApiResponse.<ProjectResponse>builder()
//                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
//                .entity(projectService.changeProjectToInactiveStatus(projectId))
//                .build();
//    }
//}
