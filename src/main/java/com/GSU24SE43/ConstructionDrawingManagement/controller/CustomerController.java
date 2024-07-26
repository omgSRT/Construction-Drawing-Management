package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CustomerRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.CustomerResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
    final CustomerService customerService;

    @Operation(summary = "Create Customer", description = "Create A Brand New Customer")
    @PostMapping(path = "/create")
    public ApiResponse<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest request){
        return ApiResponse.<CustomerResponse>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(customerService.createCustomer(request))
                .build();
    }

    @Operation(summary = "Get All Customers")
    @GetMapping(path = "/getall")
    public ApiResponse<List<CustomerResponse>> getAllCustomers(@RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<CustomerResponse>>builder()
                .entity(customerService.getAllCustomers(page, perPage))
                .build();
    }

    @Operation(summary = "Get All Customers By Name")
    @GetMapping(path = "/getall/{customerName}")
    public ApiResponse<List<CustomerResponse>> getAllCustomersByName(@RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "10") int perPage,
                                                                       @PathVariable String customerName){
        return ApiResponse.<List<CustomerResponse>>builder()
                .entity(customerService.getAllCustomersByName(page, perPage, customerName))
                .build();
    }

    @Operation(summary = "Get Customer", description = "Get Customer By ID")
    @GetMapping(path = "/get/{customerId}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable UUID customerId){
        return ApiResponse.<CustomerResponse>builder()
                .entity(customerService.getCustomerById(customerId))
                .build();
    }

    @Operation(summary = "Delete Customer", description = "Delete Customer By ID")
    @DeleteMapping(path = "/delete/{customerId}")
    public ApiResponse<CustomerResponse> deleteCustomerById(@PathVariable UUID customerId){
        return ApiResponse.<CustomerResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(customerService.deleteCustomerById(customerId))
                .build();
    }

    @Operation(summary = "Update Customer", description = "Update Customer By ID")
    @PutMapping(path = "/update/{customerId}")
    public ApiResponse<CustomerResponse> updateCustomerById(@PathVariable UUID customerId,
                                                              @RequestBody @Valid CustomerRequest request){
        return ApiResponse.<CustomerResponse>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(customerService.updateCustomerById(customerId, request))
                .build();
    }
}
