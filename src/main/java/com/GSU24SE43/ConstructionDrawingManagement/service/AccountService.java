package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.*;
import com.GSU24SE43.ConstructionDrawingManagement.enums.AccountStatus;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Role;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.AccountMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AccountService {
    AccountRepository repository;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder;

    public AccountCreateResponse accountCreateResponse(AccountCreateRequest request){
        Date now = new Date();
        boolean checkAccountName = repository.existsByUsername(request.getUsername());
        if(checkAccountName){
            throw new AppException(ErrorCode.ACCOUNT_ARE_EXISTED);
        }
        Account account = accountMapper.toAccount(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRoleName(Role.DESIGNER.name());
        account.setCreatedDate(now);
        account.setAccountStatus(AccountStatus.ACTIVE.name());
        String status = account.getAccountStatus();

//        account.builder()
//                .password(passwordEncoder.encode(request.getPassword()))
//                .roleName(Role.DESIGNER.name())
//                .createdDate(now)
//                .accountStatus("Active")
//                .build();
        account = repository.save(account);

        return accountMapper.toCreateAccountResponse(account);
    }

    public AccountUpdateResponse accountUpdateResponse(UUID accountId, AccountUpdateRequest request){
        Account account = repository.findById(accountId).orElseThrow(()
                -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));
        accountMapper.updateAccount(account, request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(account);
        return accountMapper.toAccountUpdateResponse(account);
    }

    public AccountUpdateStatusResponse updateStatus(UUID id, AccountUpdateStatusRequest request){
        Account account = repository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));
        String status = request.getAccountStatus();
        if (!status.equals(AccountStatus.ACTIVE.name())
                && (!status.equals(AccountStatus.UN_ACTIVE.name())
                && (!status.equals(AccountStatus.HIDDEN.name())
        ))){
            throw new AppException(ErrorCode.UNDEFINE_STATUS_ACCOUNT);
        }
        accountMapper.toAccountUpdateStatusResponse(account, request);
        repository.save(account);
        return accountMapper.toAccountUpdateStatusResponse(account);
    }

    public List<AccountResponse> getAllAccount(){
        return repository.findAll().stream().map(accountMapper::toAccountResponse).toList();
    }

//    @PostAuthorize("returnObject.username == authentication.name")
    public AccountResponse getAccountInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = repository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST)
        );
        return accountMapper.toAccountResponse(account);
    }

    public List<Account> searchAccount(String username ){
        List<Account> list = repository.findByUsernameContainingIgnoreCase(username);
        return list;
    }
    public void deleteAccount(UUID id){
        repository.deleteById(id);
    }



}