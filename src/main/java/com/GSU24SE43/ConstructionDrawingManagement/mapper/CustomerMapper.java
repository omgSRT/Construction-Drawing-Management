package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CustomerRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.CustomerResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerRequest request);
    
    CustomerResponse toCustomerResponse(Customer customer);

    @Mapping(target = "projectCustomers", ignore = true)
    void updateCustomer(@MappingTarget Customer customer, CustomerRequest request);
}
