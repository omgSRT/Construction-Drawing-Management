package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccountCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccountUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccountUpdateStatusRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.*;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(AccountCreateRequest request);
    AccountCreateResponse toCreateResponse(Account account);
    @Mapping(source = "staff.staffId", target = "staffId")
    AccountUpdateResponse toAccountUpdateResponse(Account account);
//    @Mapping(target = "roleName", ignore = true)
    void updateAccount(@MappingTarget Account account, AccountUpdateRequest request);
    @Mapping(source = "staff.staffId", target = "staffId")
    AccountResponse toAccountResponse(Account account);

    void toAccountUpdateStatusResponse(@MappingTarget Account account, AccountUpdateStatusRequest request);
    AccountUpdateStatusResponse toAccountUpdateStatusResponse(Account account);
}
