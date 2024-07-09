package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffFolderCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffFolderUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffFolderCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffFolderUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffFolderViewResponse;
import com.GSU24SE43.ConstructionDrawingManagement.service.StaffFolderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/staffFolder")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffFolderController {
    StaffFolderService staffFolderService;

    @PostMapping("/create")
    public ApiResponse<StaffFolderCreateResponse> create(@RequestBody StaffFolderCreateRequest request){
        return ApiResponse.<StaffFolderCreateResponse>builder()
                .entity(staffFolderService.createStaffFolder(request))
                .build();
    }

    @PutMapping("/update/{staffFolderId}")
    public ApiResponse<StaffFolderUpdateResponse> update(@PathVariable UUID staffFolderId, @RequestBody StaffFolderUpdateRequest request){
        return ApiResponse.<StaffFolderUpdateResponse>builder()
                .entity(staffFolderService.staffFolderUpdate(staffFolderId,request))
                .build();
    }

    @GetMapping("/getAll")
    public ApiResponse<List<StaffFolderViewResponse>> getAll(){
        return ApiResponse.<List<StaffFolderViewResponse>>builder()
                .entity(staffFolderService.getAll())
                .build();
    }

    @DeleteMapping("/delete/{staffFolderId}")
    public ApiResponse<Void> delete(@PathVariable UUID staffFolderId){
        staffFolderService.delete(staffFolderId);
        return ApiResponse.<Void>builder()
                .message("")
                .build();
    }
}
