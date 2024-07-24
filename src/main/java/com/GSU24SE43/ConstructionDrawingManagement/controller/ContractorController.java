package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ContractorRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ContractorResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.ContractorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contractor")
@Slf4j
public class ContractorController {
    final ContractorService contractorService;

    @Operation(summary = "Create Contractor", description = "Create A Brand New Contractor")
    @PostMapping(path = "/create")
    public ApiResponse<ContractorResponse> createContractor(@RequestBody @Valid ContractorRequest request){
        return ApiResponse.<ContractorResponse>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(contractorService.createContractor(request))
                .build();
    }

    @Operation(summary = "Get All Contractors")
    @GetMapping(path = "/getall")
    public ApiResponse<List<ContractorResponse>> getAllContractors(@RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<ContractorResponse>>builder()
                .entity(contractorService.getAllContractors(page, perPage))
                .build();
    }

    @Operation(summary = "Get All Contractors By Name")
    @GetMapping(path = "/getall/{contractorName}")
    public ApiResponse<List<ContractorResponse>> getAllContractorsByName(@RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "10") int perPage,
                                                                         @PathVariable String contractorName){
        return ApiResponse.<List<ContractorResponse>>builder()
                .entity(contractorService.getAllContractorsByName(page, perPage, contractorName))
                .build();
    }

    @Operation(summary = "Get Contractor", description = "Get Contractor By ID")
    @GetMapping(path = "/get/{contractorId}")
    public ApiResponse<ContractorResponse> getContractorById(@PathVariable UUID contractorId){
        return ApiResponse.<ContractorResponse>builder()
                .entity(contractorService.getContractorById(contractorId))
                .build();
    }

    @Operation(summary = "Delete Contractor", description = "Delete Contractor By ID")
    @DeleteMapping(path = "/delete/{contractorId}")
    public ApiResponse<ContractorResponse> deleteContractorById(@PathVariable UUID contractorId){
        return ApiResponse.<ContractorResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(contractorService.deleteContractorById(contractorId))
                .build();
    }

    @Operation(summary = "Update Contractor", description = "Update Contractor By ID")
    @PutMapping(path = "/update/{contractorId}")
    public ApiResponse<ContractorResponse> updateContractorById(@PathVariable UUID contractorId,
                                                                @RequestBody @Valid ContractorRequest request){
        return ApiResponse.<ContractorResponse>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(contractorService.updateContractorById(contractorId, request))
                .build();
    }
}
