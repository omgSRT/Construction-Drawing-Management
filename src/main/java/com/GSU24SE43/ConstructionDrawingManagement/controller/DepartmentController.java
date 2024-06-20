package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DepartmentRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @Operation(summary = "Get Department", description = "Get A Department by ID")
    @GetMapping(path = "/get/{id}")
    public ApiResponse<Department> getDepartmentById(@PathVariable UUID id){
        return ApiResponse.<Department>builder()
                .entity(departmentService.findDepartmentById(id))
                .build();
    }

    @Operation(summary = "Update Department", description = "Update Department by ID")
    @PutMapping(path = "/update/{id}")
    public ApiResponse<Department> updateDepartmentById(@PathVariable UUID id,
                                                        @RequestBody @Valid DepartmentRequest request){
        return ApiResponse.<Department>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(departmentService.updateDepartmentById(id, request))
                .build();
    }

    @Operation(summary = "Delete Department", description = "Delete Department by ID")
    @DeleteMapping(path = "/delete/{id}")
    public ApiResponse<Department> deleteDepartmentById(@PathVariable UUID id){
        var department = departmentService.findDepartmentById(id);
        departmentService.deleteDepartmentById(id);

        return ApiResponse.<Department>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(department)
                .build();
    }

    @Operation(summary = "Find Departments", description = "Find Departments By Name")
    @GetMapping(path = "/search")
    public ApiResponse<List<Department>> findDepartmentsByName(@NotBlank String departmentName,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<Department>>builder()
                .message(SuccessReturnMessage.SEARCH_SUCCESS.getMessage())
                .entity(departmentService.findDepartmentByNameContaining(departmentName, page, perPage))
                .build();
    }
}
