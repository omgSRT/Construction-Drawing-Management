package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CustomerRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.CustomerResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Customer;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.CustomerMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.CustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class CustomerService {
    final CustomerRepository customerRepository;
    final CustomerMapper customerMapper;
    final PaginationUtils paginationUtils = new PaginationUtils();

    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse createCustomer(CustomerRequest request){
        validateContractorExisted(request);

        Customer newCustomer = customerMapper.toCustomer(request);
        return customerMapper.toCustomerResponse(customerRepository.save(newCustomer));
    }

    private void validateContractorExisted(CustomerRequest request){
        Customer customerList = customerRepository.findByCustomerName(request.getCustomerName());
        if(customerList != null){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }
        Customer customerWithExistedPhone = customerRepository.findByPhone(request.getPhone());
        if(customerWithExistedPhone != null){
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }
        Customer customerWithExistedEmail = customerRepository.findByEmail(request.getEmail());
        if(customerWithExistedEmail != null){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CustomerResponse> getAllCustomers(int page, int perPage){
        List<CustomerResponse> customerResponseList = customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponse).toList();
        customerResponseList = paginationUtils.convertListToPage(page, perPage, customerResponseList);
        return customerResponseList;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CustomerResponse> getAllCustomersByName(int page, int perPage, String name){
        List<CustomerResponse> customerResponseList = customerRepository.findByCustomerNameContaining(name).stream()
                .map(customerMapper::toCustomerResponse).toList();
        customerResponseList = paginationUtils.convertListToPage(page, perPage, customerResponseList);
        return customerResponseList;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse getCustomerById(UUID customerId){
        return customerMapper.toCustomerResponse(customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse deleteCustomerById(UUID customerId){
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        customerRepository.delete(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse updateCustomerById(UUID customerId, CustomerRequest request){
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        if(!customer.getCustomerName().equalsIgnoreCase(request.getCustomerName())){
            Customer existedCustomer = customerRepository.findByCustomerName(request.getCustomerName());
            if(existedCustomer != null){
                throw new AppException(ErrorCode.NAME_EXISTED);
            }
        }

        customerMapper.updateCustomer(customer, request);

        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

}
