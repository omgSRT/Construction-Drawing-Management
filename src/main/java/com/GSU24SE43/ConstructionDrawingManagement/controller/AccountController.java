package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.*;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Role;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;
import com.GSU24SE43.ConstructionDrawingManagement.service.AccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @PostMapping("/create-account")
    public ApiResponse<AccountCreateResponse> createAccount( @RequestBody @Valid AccountCreateRequest request,@RequestParam Role role){
        return ApiResponse.<AccountCreateResponse>builder()
                .entity(accountService.accountCreateResponse(request))
                .build();
    }
    @PutMapping("/{accountId}")
    public ApiResponse<AccountUpdateResponse> updateAccount(@PathVariable UUID accountId, @RequestBody @Valid AccountUpdateRequest request){
        return ApiResponse.<AccountUpdateResponse>builder()
                .entity(accountService.accountUpdateResponse(accountId, request))
                .build();
    }
    @PutMapping("/update-status/{accountId}")
    public ApiResponse<AccountUpdateStatusResponse> updateStatus(@PathVariable UUID accountId,@RequestBody AccountUpdateStatusRequest request){
        return ApiResponse.<AccountUpdateStatusResponse>builder()
                .entity(accountService.updateStatus(accountId,request))
                .build();
    }
    @PutMapping("/update-role/{accountId}")
    public ApiResponse<AccountUpdateResponse> updateRoleAccount(@PathVariable UUID accountId,@RequestBody AccountUpdateRoleRequest request){
        return ApiResponse.<AccountUpdateResponse>builder()
                .entity(accountService.updateRole(accountId,request))
                .build();
    }

    @GetMapping("/getAllAccount")
    public ApiResponse<List<AccountResponse>> getAllAccount(){
        return ApiResponse.<List<AccountResponse>>builder()
                .entity(accountService.getAllAccount())
                .build();
    }
    @GetMapping("/myAccount")
    public ApiResponse<AccountResponse> myInfo(){
        return ApiResponse.<AccountResponse>builder()
                .entity(accountService.getAccountInfo())
                .build();
    }
    @GetMapping("/search")
    public ApiResponse<List<Account>> searchAccount(@RequestParam String username){

        List<Account> list = accountService.searchAccount(username);
        return ApiResponse.<List<Account>>builder()
                .entity(list)
                .build();
    }


    @DeleteMapping("/{accountId}")
    public ApiResponse<Void> deleteAccount(@PathVariable UUID accountId){
        accountService.deleteAccount(accountId);
        return ApiResponse.<Void>builder()
                .message("delete success!")
                .build();
    }


}
