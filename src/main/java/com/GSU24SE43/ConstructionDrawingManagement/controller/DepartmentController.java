package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DepartmentRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController {
    final DepartmentService departmentService;

    @Operation(summary = "Create New Department", description = "Create A Brand New Department")
    @PostMapping(path = "/create")
    public ApiResponse<Department> createDepartment(@RequestBody @Valid DepartmentRequest request){
        return ApiResponse.<Department>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(departmentService.createDepartment(request))
                .build();
    }

    @Operation(summary = "Get All Departments", description = "Get All Departments")
    @GetMapping(path = "/getall")
    public ApiResponse<List<Department>> getAllProjects(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<Department>>builder()
                .message(SuccessReturnMessage.SEARCH_SUCCESS.getMessage())
                .entity(departmentService.getAllDepartment(page, perPage))
                .build();
    }
}
