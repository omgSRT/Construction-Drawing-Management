package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, UUID> {
    Contractor findByContractorName(String name);

    List<Contractor> findByContractorNameContaining(String name);

    Contractor findByTaxIdentificationNumber(String tax_identification_number);
    Contractor findByPhone(String phone);
    Contractor findByEmail(String email);
}