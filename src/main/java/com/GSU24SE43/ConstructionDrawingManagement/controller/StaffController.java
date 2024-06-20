package com.GSU24SE43.ConstructionDrawingManagement.controller;
//
//import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffCreateRequest;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffUpdateRequest;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffCreateResponse2;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffUpdateResponse;
//import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
//import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
//import com.GSU24SE43.ConstructionDrawingManagement.service.AccountService;
//import com.GSU24SE43.ConstructionDrawingManagement.service.StaffService;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/staff")
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class StaffController {
//    @Autowired
//    StaffService staffService;
//    AccountService accountService;
//    @PostMapping("/create")
//    public ApiResponse<StaffCreateResponse2> create(@RequestBody StaffCreateRequest request){
//        UUID accountId = request.getAccountId();
//        Account account = accountService.getAccountByUUID(accountId);
//        return ApiResponse.<StaffCreateResponse2>builder()
//                .entity(staffService.create(account,request))
//                .build();
//    }
//    @GetMapping("/staffs")
//    public ApiResponse<List<Staff>> getAll(){
//        return ApiResponse.<List<Staff>>builder()
//                .entity(staffService.getAll())
//                .build();
//    }
//    @PutMapping("/{staffId}")
//    public ApiResponse<StaffUpdateResponse> update(@PathVariable UUID staffId, @RequestBody StaffUpdateRequest request){
//        return ApiResponse.<StaffUpdateResponse>builder()
//                .entity(staffService.update(staffId,request))
//                .build();
//    }
//    @GetMapping("/search")
//    public ApiResponse<List<Staff>> search(@RequestParam String fullname){
//        return ApiResponse.<List<Staff>>builder()
//                .entity(staffService.searchStaff(fullname))
//                .build();
//    }
//
//    @DeleteMapping("/{staffId}")
//    public ApiResponse<Void> deleteStaff(@PathVariable UUID staffId){
//        staffService.deleteStaff(staffId);
//        return ApiResponse.<Void>builder()
//                .message("xoá thành công")
//                .build();
//    }
//}
