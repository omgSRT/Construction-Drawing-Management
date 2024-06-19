package com.GSU24SE43.ConstructionDrawingManagement.controller;
//
//import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccessCreateRequest;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccessUpdateRequest;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessCreateResponse;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessResponse;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessUpdateResponse;
//import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
//import com.GSU24SE43.ConstructionDrawingManagement.service.AccessService;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//
//@RestController
//@RequestMapping("/access")
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class AccessController {
//    AccessService accessService;
//
//    @PostMapping("/create")
//    public ApiResponse<AccessCreateResponse> create(@RequestBody AccessCreateRequest request){
//        return ApiResponse.<AccessCreateResponse>builder()
//                .entity(accessService.create(request))
//                .build();
//    }
//    @GetMapping("/getAll")
//    public ApiResponse<List<AccessResponse>> getAll(){
//        return ApiResponse.<List<AccessResponse>>builder()
//                .entity(accessService.getAll())
//                .build();
//    }
//    @GetMapping("/{accessId}")
//    public ApiResponse<AccessResponse> getAccess(@PathVariable UUID accessId){
//        AccessResponse access = accessService.getById(accessId);
//        return ApiResponse.<AccessResponse>builder()
//                .entity(access)
//                .build();
//    }
//    @PutMapping("/{accessId}")
//    public ApiResponse<AccessUpdateResponse> update(@PathVariable UUID accessId, @RequestBody AccessUpdateRequest request){
//        return ApiResponse.<AccessUpdateResponse>builder()
//                .entity(accessService.updateAccess(accessId, request))
//                .build();
//    }
//    @DeleteMapping("/{accessId}")
//    public ApiResponse<Void> delete(@PathVariable UUID accessId){
//        accessService.delete(accessId);
//        return ApiResponse.<Void>builder()
//                .message("Delete success")
//                .build();
//    }
//
//
//}
