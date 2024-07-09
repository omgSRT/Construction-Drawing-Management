package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffUpdateByStaffRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.*;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.service.AccountService;
import com.GSU24SE43.ConstructionDrawingManagement.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffController {
    @Autowired
    StaffService staffService;
    AccountService accountService;
    @Operation(summary = "Create staff", description = "Create staff")
    @PostMapping("/create")
    public ApiResponse<StaffCreateResponse2> create(@RequestBody StaffCreateRequest request){
        UUID accountId = request.getAccountId();
        Account account = accountService.getAccountByUUID(accountId);
        return ApiResponse.<StaffCreateResponse2>builder()
                .entity(staffService.create(account,request))
                .build();
    }
    @Operation(summary = "Get list staff", description = "Get list staff")
    @GetMapping("/list-staff")
    public ApiResponse<List<Staff>> getAll(){
        return ApiResponse.<List<Staff>>builder()
                .entity(staffService.getAll())
                .build();
    }
    @Operation(summary = "Update staff", description = "Update staff by head and admin")
    @PutMapping("/update-by-admin/{staffId}")
    public ApiResponse<StaffUpdateResponse> update(@PathVariable UUID staffId, @RequestBody StaffUpdateRequest request){
        return ApiResponse.<StaffUpdateResponse>builder()
                .entity(staffService.update(staffId,request))
                .build();
    }
    @Operation(summary = "Update staff", description = "Update staff by staff")
    @PutMapping("/update-by-staff/{staffId}")
    public ApiResponse<StaffUpdateByStaffResponse> updateByStaff(@PathVariable UUID staffId, @RequestBody StaffUpdateByStaffRequest request){
        return ApiResponse.<StaffUpdateByStaffResponse>builder()
                .entity(staffService.updateByStaff(staffId, request))
                .build();
    }
    @Operation(summary = "Search staff", description = "search staff")
    @GetMapping("/search")
    public ApiResponse<List<Staff>> search(@RequestParam String fullname){
        return ApiResponse.<List<Staff>>builder()
                .entity(staffService.searchStaff(fullname))
                .build();
    }

    @GetMapping("/getMyStaff")
    public ApiResponse<List<StaffListResponse>> getMyStaff(){
        return ApiResponse.<List<StaffListResponse>>builder()
                .entity(staffService.getAllListStaffByHead())
                .build();
    }
    @Operation(summary = "Delete staff", description = "delete staff by admin")
    @DeleteMapping("/{staffId}")
    public ApiResponse<Void> deleteStaff(@PathVariable UUID staffId){
        staffService.deleteStaff(staffId);
        return ApiResponse.<Void>builder()
                .message("")
                .build();
    }
}
