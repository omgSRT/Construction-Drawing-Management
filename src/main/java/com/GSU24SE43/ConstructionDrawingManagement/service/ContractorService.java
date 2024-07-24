package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ContractorRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ContractorResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Contractor;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.ContractorMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.ContractorRepository;
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
public class ContractorService {
    final ContractorRepository contractorRepository;
    final ContractorMapper contractorMapper;
    final PaginationUtils paginationUtils = new PaginationUtils();

    @PreAuthorize("hasRole('ADMIN')")
    public ContractorResponse createContractor(ContractorRequest request){
        validateContractorExisted(request);

        Contractor newContractor = contractorMapper.toContractor(request);
        return contractorMapper.toContractorResponse(contractorRepository.save(newContractor));
    }

    private void validateContractorExisted(ContractorRequest request){
        Contractor contractorList = contractorRepository.findByContractorName(request.getContractorName());
        if(contractorList != null){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }
        Contractor contractorWithExistedTIN = contractorRepository.findByTaxIdentificationNumber(request.getTaxIdentificationNumber());
        if(contractorWithExistedTIN != null){
            throw new AppException(ErrorCode.TIN_EXISTED);
        }
        Contractor contractorWithExistedPhone = contractorRepository.findByPhone(request.getPhone());
        if(contractorWithExistedPhone != null){
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }
        Contractor contractorWithExistedEmail = contractorRepository.findByEmail(request.getEmail());
        if(contractorWithExistedEmail != null){
            throw new AppException(ErrorCode.TIN_EXISTED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ContractorResponse> getAllContractors(int page, int perPage){
        List<ContractorResponse> contractorResponseList = contractorRepository.findAll().stream()
                .map(contractorMapper::toContractorResponse).toList();
        contractorResponseList = paginationUtils.convertListToPage(page, perPage, contractorResponseList);
        return contractorResponseList;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ContractorResponse> getAllContractorsByName(int page, int perPage, String name){
        List<ContractorResponse> contractorResponseList = contractorRepository.findByContractorNameContaining(name).stream()
                .map(contractorMapper::toContractorResponse).toList();
        contractorResponseList = paginationUtils.convertListToPage(page, perPage, contractorResponseList);
        return contractorResponseList;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ContractorResponse getContractorById(UUID contractorId){
        return contractorMapper.toContractorResponse(contractorRepository.findById(contractorId)
                .orElseThrow(() -> new AppException(ErrorCode.CONTRACTOR_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ContractorResponse deleteContractorById(UUID contractorId){
        var contractor = contractorRepository.findById(contractorId)
                .orElseThrow(() -> new AppException(ErrorCode.CONTRACTOR_NOT_FOUND));

        contractorRepository.delete(contractor);
        return contractorMapper.toContractorResponse(contractor);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ContractorResponse updateContractorById(UUID contractorId, ContractorRequest request){
        var contractor = contractorRepository.findById(contractorId)
                .orElseThrow(() -> new AppException(ErrorCode.CONTRACTOR_NOT_FOUND));

        if(!contractor.getContractorName().equalsIgnoreCase(request.getContractorName())){
            Contractor existedContractor = contractorRepository.findByContractorName(request.getContractorName());
            if(existedContractor != null){
                throw new AppException(ErrorCode.NAME_EXISTED);
            }
        }

        contractorMapper.updateContractor(contractor, request);

        return contractorMapper.toContractorResponse(contractorRepository.save(contractor));
    }

}
