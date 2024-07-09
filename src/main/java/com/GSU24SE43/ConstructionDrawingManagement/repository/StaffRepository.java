package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {
    boolean existsByFullName(String fullname);
    List<Staff> findByFullNameContainingIgnoreCase(String fullname);
    List<Staff> findByDepartment_DepartmentId(UUID departmentId);
    boolean existsByEmail(String email);
    Staff findByAccount_AccountId(UUID accountId);
    Staff findByAccountAccountId(UUID accountId);
    Staff findByFullName(String fullname);


}
