package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccessCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.service.AccessService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/access")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessController {
    AccessService accessService;

    @PostMapping("/create")
    public ApiResponse<AccessCreateResponse> create(@RequestBody AccessCreateRequest request){
        return ApiResponse.<AccessCreateResponse>builder()
                .entity(accessService.create(request))
                .build();
    }

}
