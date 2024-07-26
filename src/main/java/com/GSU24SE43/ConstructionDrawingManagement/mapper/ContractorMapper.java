package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ContractorRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ContractorResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Contractor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContractorMapper {
    Contractor toContractor(ContractorRequest request);
    
    ContractorResponse toContractorResponse(Contractor contractor);

    @Mapping(target = "projectContractors", ignore = true)
    void updateContractor(@MappingTarget Contractor contractor, ContractorRequest request);
}
